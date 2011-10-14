/*******************************************************************************
 * Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.emf.ecore.xcore.ui.contentassist;


import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xcore.XAnnotationDirective;
import org.eclipse.emf.ecore.xcore.XClassifier;
import org.eclipse.emf.ecore.xcore.XImportDirective;
import org.eclipse.emf.ecore.xcore.XPackage;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITextViewerExtension;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.xtext.common.types.xtext.ui.JdtTypesProposalProvider;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.impl.QualifiedNameValueConverter;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal.IReplacementTextApplier;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;

import com.google.inject.Inject;


/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
public class ImportingTypesProposalProvider extends JdtTypesProposalProvider
{

  @Inject
  private QualifiedNameValueConverter qualifiedNameValueConverter;

  @Override
  protected IReplacementTextApplier createTextApplier(
    ContentAssistContext context,
    IScope typeScope,
    IQualifiedNameConverter qualifiedNameConverter,
    IValueConverter<String> valueConverter)
  {
    return new FQNImporter(
      context.getResource(),
      context.getViewer(),
      typeScope,
      qualifiedNameConverter,
      valueConverter,
      qualifiedNameValueConverter);
  }

  public static class FQNImporter extends FQNShortener
  {

    private final ITextViewer viewer;

    private final QualifiedNameValueConverter importConverter;

    public FQNImporter(
      Resource context,
      ITextViewer viewer,
      IScope scope,
      IQualifiedNameConverter qualifiedNameConverter,
      IValueConverter<String> valueConverter,
      QualifiedNameValueConverter importConverter)
    {
      super(context, scope, qualifiedNameConverter, valueConverter);
      this.viewer = viewer;
      this.importConverter = importConverter;
    }

    @Override
    public void apply(IDocument document, ConfigurableCompletionProposal proposal) throws BadLocationException
    {
      String proposalReplacementString = proposal.getReplacementString();
      String typeName = proposalReplacementString;
      if (valueConverter != null)
        typeName = valueConverter.toValue(proposalReplacementString, null);
      String replacementString = getActualReplacementString(proposal);
      // there is an import statement - apply computed replacementString
      if (!proposalReplacementString.equals(replacementString))
      {
        String shortTypeName = replacementString;
        if (valueConverter != null)
          shortTypeName = valueConverter.toValue(replacementString, null);
        QualifiedName shortQualifiedName = qualifiedNameConverter.toQualifiedName(shortTypeName);
        if (shortQualifiedName.getSegmentCount() == 1)
        {
          proposal.setCursorPosition(replacementString.length());
          document.replace(proposal.getReplacementOffset(), proposal.getReplacementLength(), replacementString);
          return;
        }
      }

      QualifiedName qualifiedName = qualifiedNameConverter.toQualifiedName(typeName);
      if (qualifiedName.getSegmentCount() == 1)
      {
        // type resides in default package - no need to hassle with imports
        proposal.setCursorPosition(proposalReplacementString.length());
        document.replace(proposal.getReplacementOffset(), proposal.getReplacementLength(), proposalReplacementString);
        return;
      }

      IEObjectDescription description = scope.getSingleElement(qualifiedName.skipFirst(qualifiedName.getSegmentCount() - 1));
      if (description != null)
      {
        // there exists a conflict - insert fully qualified name
        proposal.setCursorPosition(proposalReplacementString.length());
        document.replace(proposal.getReplacementOffset(), proposal.getReplacementLength(), proposalReplacementString);
        return;
      }

      // Import does not introduce ambiguities - add import and insert short name
      String shortName = qualifiedName.getLastSegment();
      int topPixel = -1;
      // store the pixel coordinates to prevent the ui from flickering
      StyledText widget = viewer.getTextWidget();
      if (widget != null)
        topPixel = widget.getTopPixel();
      ITextViewerExtension viewerExtension = null;
      if (viewer instanceof ITextViewerExtension)
      {
        viewerExtension = (ITextViewerExtension)viewer;
        viewerExtension.setRedraw(false);
      }
      try
      {
        // compute import statement's offset
        int offset = 0;
        boolean startWithLineBreak = true;
        boolean endWithLineBreak = false;
        XPackage file = (XPackage)context.getContents().get(0);
        EList<XImportDirective> importDirectives = file.getImportDirectives();
        if (importDirectives.isEmpty())
        {
          startWithLineBreak = false;
          EList<XAnnotationDirective> annotationDirectives = file.getAnnotationDirectives();
          if (annotationDirectives.isEmpty())
          {
            EList<XClassifier> classifiers = file.getClassifiers();
            if (classifiers.isEmpty())
            {
              offset = document.getLength();
            }
            else
            {
              ICompositeNode node = NodeModelUtils.getNode(classifiers.get(0));
              offset = node.getOffset();
              endWithLineBreak = true;
            }
          }
          else
          {
            ICompositeNode node = NodeModelUtils.getNode(annotationDirectives.get(0));
            offset = node.getOffset();
            endWithLineBreak = true;
          }
        }
        else
        {
          ICompositeNode node = NodeModelUtils.getNode(importDirectives.get(importDirectives.size() - 1));
          offset = node.getOffset() + node.getLength();
        }
        offset = Math.min(proposal.getReplacementOffset(), offset);

        // apply short proposal
        String escapedShortname = shortName;
        if (valueConverter != null)
        {
          escapedShortname = valueConverter.toString(shortName);
        }
        proposal.setCursorPosition(escapedShortname.length());
        document.replace(proposal.getReplacementOffset(), proposal.getReplacementLength(), escapedShortname);

        // add import statement
        String importStatement = (startWithLineBreak ? "\nimport " : "import ") + importConverter.toString(typeName);
        if (endWithLineBreak)
          importStatement += "\n\n";
        document.replace(offset, 0, importStatement.toString());
        proposal.setCursorPosition(proposal.getCursorPosition() + importStatement.length());

        // set the pixel coordinates
        if (widget != null)
        {
          int additionalTopPixel = 0;
          if (startWithLineBreak)
            additionalTopPixel += widget.getLineHeight();
          if (endWithLineBreak)
            additionalTopPixel += 2 * widget.getLineHeight();
          widget.setTopPixel(topPixel + additionalTopPixel);
        }
      }
      finally
      {
        if (viewerExtension != null)
          viewerExtension.setRedraw(true);
      }
    }
  }
}

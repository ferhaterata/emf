/**
 * <copyright>
 *
 * Copyright (c) 2004-2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors: 
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: TestUtil.java,v 1.2 2005/02/10 22:11:11 marcelop Exp $
 */
package org.eclipse.emf.test.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.FileSet;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;

/**
 * @author marcelop
 */
public class TestUtil
{
  private static class Foo{};
  
  public static String getPluginDirectory()
  {
    try
    {
      if (Platform.isRunning())
      {
        return new java.io.File(Platform.asLocalURL(EMFTestToolsPlugin.getPlugin().getBundle().getEntry("/")).getFile()).toString();
      }
    }
    catch (Throwable t)
    {
    }
    
    URL url = new Foo().getClass().getResource(".");
    String path = url.getPath();
    path = path.substring(0, path.indexOf("org.eclipse.emf.test.tools/") + "org.eclipse.emf.test.tools/".length());
    return new File(path).getAbsolutePath();
  }
  
  public static String getPluginDirectory(String pluginID)
  {
    try
    {
      if (Platform.isRunning())
      {
        return new java.io.File(Platform.asLocalURL(Platform.getBundle(pluginID).getEntry("/")).getFile()).toString();
      }
    }
    catch (Throwable t)
    {
    }
    
    File parentDirectory = new File(getPluginDirectory());
    File[] plugins = parentDirectory.listFiles();
    for (int i = 0, maxi = plugins.length; i < maxi; i++)
    {
      if (plugins[i].isDirectory())
      {
        String name = plugins[i].getName();
        if (name.equals(pluginID) || name.startsWith(pluginID + "_"))
        {
          return plugins[i].getAbsolutePath();
        }
      }
    }
    
    return null;
  }
  
  public static String readFile(File file)
  {    
    StringBuffer stringBuffer = new StringBuffer();

    try
    {
      BufferedReader in = new BufferedReader(new FileReader(file));
      String str = null;
      
      try
      {
        while ((str = in.readLine()) != null)
        {
          stringBuffer.append(str).append("\n");
        }
      }
      finally
      {
        if (in != null)
        {
          in.close();
        }
      }
    }
    catch(IOException exception)
    {
      throw new RuntimeException(exception);
    }
    
    int length = stringBuffer.length();
    if(length > 0)
    {
      stringBuffer.deleteCharAt(length - 1);
    }
    return stringBuffer.toString();
  }  
  
  public static void delete(File file)
  {
    if (file.isDirectory())
    {
      File[] children = file.listFiles();
      for (int i = 0, maxi = children.length; i < maxi; i++)
      {
        delete(children[i]);
      }
    }
    
    if (file.exists())
    {
      file.delete();
    }
  }
  
  public static void copyFiles(File fromDir, File toDir)
  {
    Copy antCopyTask = new Copy();
    antCopyTask.setProject(new Project());
    antCopyTask.setTodir(toDir);
    FileSet fromDirFS = new FileSet();
    fromDirFS.setDir(fromDir);
    antCopyTask.addFileset(fromDirFS);
    antCopyTask.execute();    
  }
  
  public static void runAnt(File script, String arguments) throws CoreException
  {
    AntRunner antRunner = new AntRunner();
    antRunner.setBuildFileLocation(script.getAbsolutePath());
    if (arguments != null) antRunner.setArguments(arguments);
    antRunner.run(new NullProgressMonitor());
  }  
}

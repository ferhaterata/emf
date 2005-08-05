/**
 * <copyright>
 * </copyright>
 *
 * $Id: ExtPackage.java,v 1.1.2.1 2005/08/05 20:36:34 nickb Exp $
 */
package org.eclipse.emf.test.models.ext;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.test.models.ref.RefPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.test.models.ext.ExtFactory
 * @model kind="package"
 * @generated
 */
public interface ExtPackage extends EPackage{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "ext";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http:///org/eclipse/emf/test/models/ext.ecore";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "org.eclipse.emf.test.models.ext";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ExtPackage eINSTANCE = org.eclipse.emf.test.models.ext.impl.ExtPackageImpl.init();

  /**
   * The meta object id for the '{@link org.eclipse.emf.test.models.ext.impl.ExtEImpl <em>E</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.test.models.ext.impl.ExtEImpl
   * @see org.eclipse.emf.test.models.ext.impl.ExtPackageImpl#getExtE()
   * @generated
   */
  int EXT_E = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXT_E__NAME = RefPackage.E__NAME;

  /**
   * The feature id for the '<em><b>Ids</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXT_E__IDS = RefPackage.E__IDS;

  /**
   * The feature id for the '<em><b>Labels</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXT_E__LABELS = RefPackage.E__LABELS;

  /**
   * The feature id for the '<em><b>D</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXT_E__D = RefPackage.E__D;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXT_E__VALUE = RefPackage.E_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>F</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXT_E__F = RefPackage.E_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the the '<em>E</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXT_E_FEATURE_COUNT = RefPackage.E_FEATURE_COUNT + 2;


  /**
   * The meta object id for the '{@link org.eclipse.emf.test.models.ext.impl.FImpl <em>F</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.test.models.ext.impl.FImpl
   * @see org.eclipse.emf.test.models.ext.impl.ExtPackageImpl#getF()
   * @generated
   */
  int F = 1;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int F__ID = 0;

  /**
   * The feature id for the '<em><b>E</b></em>' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int F__E = 1;

  /**
   * The number of structural features of the the '<em>F</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int F_FEATURE_COUNT = 2;


  /**
   * Returns the meta object for class '{@link org.eclipse.emf.test.models.ext.ExtE <em>E</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>E</em>'.
   * @see org.eclipse.emf.test.models.ext.ExtE
   * @generated
   */
  EClass getExtE();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.test.models.ext.ExtE#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see org.eclipse.emf.test.models.ext.ExtE#getValue()
   * @see #getExtE()
   * @generated
   */
  EAttribute getExtE_Value();

  /**
   * Returns the meta object for the containment reference list '{@link org.eclipse.emf.test.models.ext.ExtE#getF <em>F</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>F</em>'.
   * @see org.eclipse.emf.test.models.ext.ExtE#getF()
   * @see #getExtE()
   * @generated
   */
  EReference getExtE_F();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.test.models.ext.F <em>F</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>F</em>'.
   * @see org.eclipse.emf.test.models.ext.F
   * @generated
   */
  EClass getF();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.test.models.ext.F#getId <em>Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Id</em>'.
   * @see org.eclipse.emf.test.models.ext.F#getId()
   * @see #getF()
   * @generated
   */
  EAttribute getF_Id();

  /**
   * Returns the meta object for the container reference '{@link org.eclipse.emf.test.models.ext.F#getE <em>E</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the container reference '<em>E</em>'.
   * @see org.eclipse.emf.test.models.ext.F#getE()
   * @see #getF()
   * @generated
   */
  EReference getF_E();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  ExtFactory getExtFactory();

} //ExtPackage

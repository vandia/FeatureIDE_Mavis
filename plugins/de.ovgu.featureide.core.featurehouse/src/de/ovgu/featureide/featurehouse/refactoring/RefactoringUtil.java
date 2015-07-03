/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2015  FeatureIDE team, University of Magdeburg, Germany
 *
 * This file is part of FeatureIDE.
 * 
 * FeatureIDE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * FeatureIDE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with FeatureIDE.  If not, see <http://www.gnu.org/licenses/>.
 *
 * See http://featureide.cs.ovgu.de/ for further information.
 */
package de.ovgu.featureide.featurehouse.refactoring;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import de.ovgu.featureide.core.signature.base.AbstractClassSignature;
import de.ovgu.featureide.core.signature.base.AbstractMethodSignature;
import de.ovgu.featureide.core.signature.base.AbstractSignature;
import de.ovgu.featureide.featurehouse.signature.fuji.FujiClassSignature;
import de.ovgu.featureide.featurehouse.signature.fuji.FujiMethodSignature;

/**
 * TODO description
 * 
 * @author steffen
 */
public class RefactoringUtil {

	
	public static CompilationUnit parseUnit(ICompilationUnit unit) {
	    ASTParser parser = ASTParser.newParser(AST.JLS4);
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    parser.setSource(unit);
	    parser.setResolveBindings(true);
	    parser.setBindingsRecovery(true);
	    return (CompilationUnit) parser.createAST(null); 
	  }
	
	public static boolean hasSameClass(final AbstractSignature signature, final IMember member) {
		final IType declaringType = member.getDeclaringType();
		String className;
		if (declaringType != null)
			className = member.getDeclaringType().getElementName();
		else
			className = member.getElementName();
		
		String sigClassName = signature.getName();
		AbstractClassSignature classSig = signature.getParent();
		if (classSig != null) {
			sigClassName = classSig.getName();
		}
		
		return sigClassName.equals(className) && hasSamePackage(signature, member);
	}
	
	private static boolean hasSamePackage(final AbstractSignature signature, final IMember member) {
		
		final String sigPackage = getPackage(signature);
		final String package0 = getPackageDeclaration(member.getCompilationUnit());
		
		return sigPackage.equals(package0);
	}
	
	public static boolean hasSamePackage(final AbstractSignature signature1, final AbstractSignature signature2) {
		
		final String sigPackage1 = getPackage(signature1);
		final String sigPackage2 = getPackage(signature2);
		
		return sigPackage1.equals(sigPackage2);
	}
	
	public static String getPackage(final AbstractSignature signature){
		if (signature instanceof FujiClassSignature)
			return ((FujiClassSignature) signature).getPackage();
		else
			return signature.getParent().getPackage();
	}

	public static String getPackageDeclaration(final ICompilationUnit unit) {
		IPackageDeclaration[] packageDeclarations = null;
		try {
			packageDeclarations = unit.getPackageDeclarations();
		} catch (JavaModelException e) {
			//TODO: 
			e.printStackTrace();
		}
		
		String package0 = "";
		if ((packageDeclarations != null) && (packageDeclarations.length > 0))
			package0 = packageDeclarations[0].getElementName();
		return package0;
	}
	
	public static boolean hasSameName(final AbstractSignature signature, final IMember member) {
		return hasSameName(signature, member.getElementName());
	}
	
	public static boolean hasSameName(final AbstractSignature signature1, final AbstractSignature signature2) {
		return hasSameName(signature1.getName(), signature2.getName());
	}
	
	public static boolean hasSameName(final AbstractSignature signature, final String name) {
		return signature.getName().equals(name);
	}
	
	private static boolean hasSameName(final String name1, final String name2) {
		return name1.equals(name2);
	}
	
	public static boolean hasSameParameters(final FujiMethodSignature signature, final IMethod method) {
		List<String> parameterTypes = signature.getParameterTypes();

		final int myParamsLength = method.getParameterTypes().length;
		final String[] simpleNames = new String[myParamsLength];
		for (int i = 0; i < myParamsLength; i++) {
			String erasure = Signature.getTypeErasure(method.getParameterTypes()[i]);
			simpleNames[i] = Signature.getSimpleName(Signature.toString(erasure));
		}

		return Arrays.equals(simpleNames, parameterTypes.toArray(new String[parameterTypes.size()]));
	}
	
	public static boolean hasSameParameters(final FujiMethodSignature signature1, final FujiMethodSignature signature2) {
		List<String> parameterTypes1 = signature1.getParameterTypes();
		List<String> parameterTypes2 = signature1.getParameterTypes();
		
		return parameterTypes1.equals(parameterTypes2);
	}
	
	/**
	 * Returns <code>true</code> iff the method could be a virtual method,
	 * i.e. if it is not a constructor, is private, or is static.
	 *
	 * @param method a method
	 * @return <code>true</code> iff the method could a virtual method
	 */
	public static boolean isVirtual(AbstractMethodSignature method) {
		if (method.isConstructor())
			return false;
		if (method.isPrivate())
			return false;
		if (method.isStatic())
			return false;
		return true;
	}

}

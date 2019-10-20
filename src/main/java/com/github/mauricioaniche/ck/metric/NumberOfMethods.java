package com.github.mauricioaniche.ck.metric;

import com.github.mauricioaniche.ck.CKClassResult;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;

public class NumberOfMethods extends ASTVisitor implements ClassLevelMetric {

	private int methods;
	private int staticMethods;
	private int publicMethods;
	private int privateMethods;
	private int protectedMethods;
	private int defaultMethods;
	private int abstractMethods;
	private int finalMethods;
	private int synchronizedMethods;

	@Override
	public boolean visit(MethodDeclaration node) {
		methods++;

		// visibility
		boolean isPublic = Modifier.isPublic(node.getModifiers());
		boolean isPrivate = Modifier.isPrivate(node.getModifiers());
		boolean isProtected = Modifier.isProtected(node.getModifiers());

		if(isPublic)
			publicMethods++;
		else if(isPrivate)
			privateMethods++;
		else if(isProtected)
			protectedMethods++;
		else
			defaultMethods++;

		// other characteristics

		boolean isFinal = Modifier.isFinal(node.getModifiers());
		boolean isSynchronized = Modifier.isSynchronized(node.getModifiers());
		boolean isAbstract = Modifier.isAbstract(node.getModifiers());
		boolean isStatic = Modifier.isStatic(node.getModifiers());

		if(isStatic)
			staticMethods++;

		if(isAbstract)
			abstractMethods++;

		if(isFinal)
			finalMethods++;

		if(isSynchronized)
			synchronizedMethods++;

		return false;
	}

	@Override
	public void execute(CompilationUnit cu, CKClassResult number) {
		cu.accept(new IgnoreSubClasses(this));
	}

	@Override
	public void setResult(CKClassResult result) {
		result.setNumberOfMethods(methods);
		result.setNumberOfStaticMethods(staticMethods);
		result.setNumberOfPublicMethods(publicMethods);
		result.setNumberOfPrivateMethods(privateMethods);
		result.setNumberOfProtectedMethods(protectedMethods);
		result.setNumberOfDefaultMethods(defaultMethods);
		result.setNumberOfAbstractMethods(abstractMethods);
		result.setNumberOfFinalMethods(finalMethods);
		result.setNumberOfSynchronizedMethods(synchronizedMethods);

	}
}

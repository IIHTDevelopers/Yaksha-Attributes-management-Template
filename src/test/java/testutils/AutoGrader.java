package testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;

public class AutoGrader {

	// Test if the code uses class creation, object creation, and method invocations
	// correctly
	public boolean testClassAndAttributesCreation(String filePath) throws IOException {
		System.out.println("Starting testClassAndObjectCreation with file: " + filePath);

		File participantFile = new File(filePath); // Path to participant's file
		if (!participantFile.exists()) {
			System.out.println("File does not exist at path: " + filePath);
			return false;
		}

		FileInputStream fileInputStream = new FileInputStream(participantFile);
		JavaParser javaParser = new JavaParser();
		CompilationUnit cu;
		try {
			cu = javaParser.parse(fileInputStream).getResult()
					.orElseThrow(() -> new IOException("Failed to parse the Java file"));
		} catch (IOException e) {
			System.out.println("Error parsing the file: " + e.getMessage());
			throw e;
		}

		System.out.println("Parsed the Java file successfully.");

		boolean hasClassAndObjectOperations = false;

		// Check for class creation (Person and Address classes)
		System.out.println("------ Class Creation ------");
		boolean personClassFound = false;
		boolean addressClassFound = false;
		for (TypeDeclaration<?> typeDecl : cu.findAll(TypeDeclaration.class)) {
			if (typeDecl.getNameAsString().equals("Person")) {
				System.out.println("Class 'Person' found: " + typeDecl.getName());
				hasClassAndObjectOperations = true;
				personClassFound = true;
			}
			if (typeDecl.getNameAsString().equals("Address")) {
				System.out.println("Class 'Address' found: " + typeDecl.getName());
				hasClassAndObjectOperations = true;
				addressClassFound = true;
			}
		}

		if (!personClassFound || !addressClassFound) {
			System.out.println("Error: Required classes 'Person' or 'Address' not found.");
			return false; // Early exit if class creation is missing
		}

		// Check for object creation (using 'new Person(...)' and 'new Address(...)')
		System.out.println("------ Object Creation ------");
		boolean personObjectCreated = false;
		boolean addressObjectCreated = false;
		for (ObjectCreationExpr objectCreation : cu.findAll(ObjectCreationExpr.class)) {
			if (objectCreation.getType().getNameAsString().equals("Person")) {
				System.out.println("Object created using 'new Person(...)': " + objectCreation);
				hasClassAndObjectOperations = true;
				personObjectCreated = true;
			}
			if (objectCreation.getType().getNameAsString().equals("Address")) {
				System.out.println("Object created using 'new Address(...)': " + objectCreation);
				hasClassAndObjectOperations = true;
				addressObjectCreated = true;
			}
		}
		if (!personObjectCreated || !addressObjectCreated) {
			System.out.println("Error: Object creation for 'Person' or 'Address' is missing.");
			return false; // Early exit if object creation is missing
		}

		// Check for method invocations (displayDetails and modifyDetails methods)
		System.out.println("------ Method Invocation ------");
		boolean displayDetailsCalled = false;
		boolean modifyDetailsCalled = false;
		boolean modifyAddressCalled = false;
		for (ExpressionStmt stmt : cu.findAll(ExpressionStmt.class)) {
			if (stmt.getExpression() instanceof MethodCallExpr) {
				MethodCallExpr methodCall = (MethodCallExpr) stmt.getExpression();
				if (methodCall.getNameAsString().equals("displayDetails")) {
					System.out.println("Method 'displayDetails' called: " + methodCall);
					hasClassAndObjectOperations = true;
					displayDetailsCalled = true;
				}
				if (methodCall.getNameAsString().equals("modifyDetails")) {
					System.out.println("Method 'modifyDetails' called: " + methodCall);
					modifyDetailsCalled = true;
				}
				if (methodCall.getNameAsString().equals("modifyAddress")) {
					System.out.println("Method 'modifyAddress' called: " + methodCall);
					modifyAddressCalled = true;
				}
			}
		}

		if (!displayDetailsCalled) {
			System.out.println("Error: 'displayDetails' method is not called.");
			return false; // Early exit if method invocation is missing
		}
		if (!modifyDetailsCalled) {
			System.out.println("Error: 'modifyDetails' method is not called.");
			return false;
		}
		if (!modifyAddressCalled) {
			System.out.println("Error: 'modifyAddress' method is not called.");
			return false;
		}

		// If all operations were found, return true
		System.out.println("Test passed: All required class, object, and method operations were found.");
		return true;
	}
}

package de.simonmeusel.vector.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
	private static Application instance;
	
	public static Application getInstance() {
		if (instance == null) {
			throw new IllegalArgumentException();
		}
		return instance;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		instance = this;
		
        primaryStage.setScene(new Scene(createDocumentLoader(new DocumentController()).load()));
        primaryStage.show();
	}
	
	private FXMLLoader createDocumentLoader(DocumentController documentController) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Document.fxml"));
		loader.setController(documentController);
		return loader;
	}
	
	public Stage createDocumentStage(DocumentController documentController) throws IOException {
		Stage stage = new Stage();
		stage.setScene(new Scene(createDocumentLoader(documentController).load()));
		stage.show();
		
		return stage;
	}

}

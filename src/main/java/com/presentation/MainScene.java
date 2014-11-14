package com.presentation;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mathworks.toolbox.javabuilder.MWNumericArray;
import com.matlab.functions.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainScene extends Application {

	public static void main(String[] args) {
		// Application.launch(MainScene.class, (java.lang.String[]) null);
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"ApplicationContext.xml");
		MatlabAPI a = (MatlabAPI) applicationContext.getBean("MatlabAPI");
		Object[] res = null;
		// res=a.getStatisticalFeatures("C:\\11.png", 20);
		// res=a.readImage("C:\\11.png");
		res = a.getHomomorphicFilter("C:\\11.png", 10d);
		MWNumericArray filt = (MWNumericArray) (res[0]);
		// System.out.println(res[0]);
		getImageFromMWNumericArray(filt);
	}

	// setenv('JAVA_HOME','C:\Program Files\Java\jdk1.6.0_32');
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(MainScene.class
					.getResource("MainWindow.fxml"));
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Surf");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception ex) {
			Logger.getLogger(MainScene.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}
	
	public static Image getImageFromMWNumericArray(MWNumericArray array) {
		int[] dim = array.getDimensions();

		int[] res = new int[dim[0] * dim[1]];
		int s = 0;
		for (int i = 0; i < dim[0] * dim[1]; i++) {
			int g = array.getInt(i + 1);
			res[i] = GetColorFromRGB(g, g, g);
		}

		writeImageToFile(getImageFromArray(res, dim[0], dim[1]), "png", "C:\\out.png");;
		System.out.println(res[0] + " " + res[1] + " " + res[2]);
		System.out.println(dim[0] * dim[1]);
		return null;
	}

	private static int GetColorFromRGB(int r, int g, int b) {
		int col = (r << 16) | (g << 8) | b;
		return col;
	}

	public static BufferedImage getImageFromArray(int[] mas, int ih, int iw) {
		BufferedImage image = new BufferedImage(iw, ih,
				BufferedImage.TYPE_INT_RGB);
		int s = 0;
		for (int i = 0; i < ih; i++) {
			for (int j = 0; j < iw; j++) {
				int g = mas[s];
				image.setRGB(i,j, GetColorFromRGB(g, g, g));
				s++;

			}
		}
		return image;
	}

	public static void writeImageToFile(BufferedImage im, String type,
			String file) {
		try {
			File file1 = new File(file);
			ImageIO.write(im, type, file1);
		} catch (IOException ex) {

		}
	}
}

package com.brejral.puertorico.save;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.brejral.puertorico.game.Game;
import com.brejral.puertorico.game.GameHelper;

public class SaveHelper {
	private static int FILE_INDEX = 0;

	public static void saveGameToFile() {
		File file = new File("TempGameSaveFile" + ++FILE_INDEX + ".svgm");
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
			out.writeObject(GameHelper.getGame());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static Game loadLastGameFromFile() {
		Game game = null;
		File file = new File("TempGameSaveFile" + --FILE_INDEX + ".svgm");
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			game = (Game) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return game;
	}
}

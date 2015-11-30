package com.joe;

public class Main {

    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();

        String drive1 = "Drive1", drive2 = "Drive2";
        String folder1 = "Folder1";
        String zipFile1 = "Zip1";
        String textFile1 = "Text1", textFile2 = "Text2", textFile3 = "Text3", textFile4 = "Text4";

        try {
            // Drive1
            fileSystem.create(EntityType.DRIVE, drive1, "");

            fileSystem.create(EntityType.FOLDER, folder1, drive1);

            fileSystem.create(EntityType.TEXTFILE, textFile1, drive1);
            fileSystem.writeToFile(drive1 + "/" + textFile1, "Hello text file!");

            String folder1Path = drive1 + "/" + folder1;
            fileSystem.create(EntityType.TEXTFILE, textFile2, folder1Path);
            String textFile2Path = folder1Path + "/" + textFile2;
            fileSystem.writeToFile(textFile2Path, "Another text file that has more content.");

            fileSystem.create(EntityType.ZIPFILE, zipFile1, drive1 + "/" + folder1);

            fileSystem.create(EntityType.TEXTFILE, textFile4, folder1Path + "/" + zipFile1);
            fileSystem.writeToFile(folder1Path + "/" + zipFile1 + "/" + textFile4, "13 characters");

            // Drive2
            fileSystem.create(EntityType.DRIVE, drive2, "");
            fileSystem.create(EntityType.TEXTFILE, textFile3, drive2);
            fileSystem.writeToFile(drive2 + "/" + textFile3, "Short");

            System.out.println("Before moving " + textFile2 + ":");
            fileSystem.listEntities();

            fileSystem.move(textFile2Path, drive2);

            System.out.println("\nAfter moving " + textFile2 + ":");
            fileSystem.listEntities();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        IdentityManager identity;
        VirtualFileSystem vfs;

        while (true) {

            String fileName;
            String capFile;
            Scanner scanner = new Scanner ( System.in );
            int numChoice, blockSize = 1;
            System.out.println ( "Please enter the number of allocation type : \n" );
            System.out.println ( "1- Contiguous Allocation \n" +
                    "2- Linked Allocation\n" +
                    "3- Indexed Allocation\n" );

            numChoice = scanner.nextInt ( );
            if ( numChoice == 1 ) {
                fileName = "contiguous.vfs";
                capFile = "capabilitiesC.txt";
                vfs = new VirtualFileSystem ( fileName , new Contigous ( ) );
            } else if ( numChoice == 2 ) {
                fileName = "linked.vfs";
                capFile = "capabilitiesL.txt";
                vfs = new VirtualFileSystem ( fileName , new Linked ( ) );
            } else {
                fileName = "indexed.vfs";
                capFile = "capabilitiesI.txt";
                vfs = new VirtualFileSystem ( fileName , new Indexed ( ) );
            }
            identity = new IdentityManager ( capFile );
            boolean flag = true;
            boolean firstTime = true;
            vfs.getFolders ( );
            while ( flag ) {                                                     //Grant ahmed VFSD:\Folder1 10
                System.out.println ( "Enter your Command: " );
                String command;
                if ( firstTime ) {
                    scanner.nextLine ( );
                    firstTime = false;
                }
                command = scanner.nextLine ( );
                String arr[] = command.split ( " " );
                String path = "", name = "";
                Directory currDirectory = null;
                if ( arr.length > 1 ) {
                    for ( int i = arr[ 1 ].length ( ) - 1 ; i >= 0 ; i-- ) {
                        if ( arr[ 1 ].charAt ( i ) == '/' ) {
                            path = arr[ 1 ].substring ( 0 , i );
                            name = arr[ 1 ].substring ( i + 1 );
                            break;

                        }
                    }
                } else if ( arr.length == 1 ) {
                    switch (arr[ 0 ]) {
                        case "TellUser":
                            identity.tellUser ( );
                            break;
                        case "DisplayDiskStatus":
                            vfs.printDiskStatus ( );
                            break;
                        case "DisplayDiskStructure":
                            vfs.displayStructure ( );
                            break;
                        case "Exit":
                            vfs.writeData ( fileName );
                            flag = false;
                            identity.shutDown ( vfs );
                            break;
                    }
                    continue;
                } else {
                    continue;
                }

                switch (arr[ 0 ]) {
                    case "CUser":
                        identity.createUser ( arr[ 1 ] , arr[ 2 ] );
                        break;
                    case "Login":
                        identity.LogIn ( arr[ 1 ] , arr[ 2 ] );
                        break;
                    case "Grant":
                        Admin ad = identity.isLoggedAdmin ( );
                        if ( ad != null ) {
                            User temp = identity.getUser ( arr[ 1 ] );
                            if ( temp != null ) {
                                if ( vfs.folderExists ( arr[ 2 ] ) ) {
                                    ad.Grant ( temp , arr[ 2 ] , Integer.parseInt ( arr[ 3 ] ) );
                                } else {
                                    System.out.println ( "Folder doesn't exist !!" );
                                }
                            } else {
                                System.out.println ( "User doesn't exist !!" );
                            }
                        } else {
                            System.out.println ( "You don't have this privilege !!" );
                        }
                        break;
                    case "CreateFile":
                        int size = (int) (Double.parseDouble ( arr[ 2 ] ) + 0.5);
                        vfs.createFile ( path , name , size , identity.currUser );
                        break;

                    case "CreateFolder":
                        vfs.createFolder ( path , name , identity.currUser );
                        break;

                    case "DeleteFile":
                        vfs.deleteFile ( path + "/" + name , identity.currUser );
                        break;

                    case "DeleteFolder":
                        vfs.deleteDirectory ( path + "/" + name , identity.currUser );
                        break;

                }

            }

            System.out.print ("Do you want to choose another Allocation Method? (Y|N): " );
            String choice;
            choice = scanner.next ( );
            if (choice.equalsIgnoreCase ( "N" )){
                break;

            }
        }



    }
}

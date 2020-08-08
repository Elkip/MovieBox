import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


public class Runner {

    public static void main(String[] args) {
        System.out.println(FileSystemView.getFileSystemView().getHomeDirectory());
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileNameExtensionFilter videofilter = new FileNameExtensionFilter(
                "mp4 files (*.mp4)", "mp4");

        jfc.setDialogTitle("Welcome to the Movie Box");
        jfc.setFileFilter(videofilter);
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
        }

    }
}

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.x.XFullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;


public class Runner {

    public static void main(String[] args) {
        LibXUtil.initialise();

        System.out.println(FileSystemView.getFileSystemView().getHomeDirectory());
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileNameExtensionFilter videofilter = new FileNameExtensionFilter(
                "mp4 files (*.mp4)", "mp4");

        jfc.setDialogTitle("Welcome to the Movie Box");
        jfc.setFileFilter(videofilter);
        int returnValue = jfc.showOpenDialog(null);

        String name = null;
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            name = selectedFile.getAbsolutePath();
        }

        if (returnValue == JFileChooser.CANCEL_OPTION) {
            return;
        }

        // GUI
        JFrame frame = new JFrame();
        frame.setLocation(100, 100);
        frame.setSize(1000,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Canvas to display video
        Canvas canvas = new Canvas();
        canvas.setBackground(Color.BLACK);
        JPanel panel = new JPanel();
        panel.add(canvas);
        frame.add(panel);

        // read the video file using vlcj
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "/usr/lib64/vlc");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        MediaPlayerFactory mpf = new MediaPlayerFactory();

        EmbeddedMediaPlayer emp = mpf.newEmbeddedMediaPlayer(new XFullScreenStrategy(frame));

        emp.setVideoSurface(mpf.newVideoSurface(canvas));
        //emp.toggleFullScreen();

        // disable mouse
        emp.setEnableMouseInputHandling(false);
        emp.prepareMedia(name);
        System.out.println("Media ready");
        emp.play();
    }
}

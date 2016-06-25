package PdfManualProcessor.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame implements View {
    private Console console;
    private ViewHandler viewHandler;

    private JButton download, filter, refresh, check,next, prev;
    private JScrollPane scroll;
    private JPanel box,openers;
    private JLabel nManuals, pManuals;
    private SpinnerModel nextModel,prevModel;
    private JSpinner nextSpinner,prevSpinner;

    public MainView(Console console, ViewHandler viewHandler) {
        super();
        this.console=console;
        this.viewHandler = viewHandler;
        init();
    }

    @Override
    public void init() {
        download = new JButton("Download manuals");
        download.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewHandler.fireEventDownloadManuals();
            }
        });
        filter = new JButton("Filter manuals");
        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewHandler.fireEventFilterManuals();
            }
        });
        refresh = new JButton("Refresh manual list");
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        viewHandler.fireEventRefreshManualList();
                    }
                }).start();

            }
        });
        check = new JButton("Check manuals");
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewHandler.fireEventCheckManuals();
            }
        });

        initOpeners(); // different abstraction level - rework.
        box = new JPanel(new GridLayout(5, 1,0,5));

        box.add(refresh);
        box.add(download);
        box.add(filter);
        box.add(check);
        box.add(openers);

        JPanel west = new JPanel(new GridBagLayout());
        west.add(box);

        scroll = new JScrollPane(console);

        getContentPane().add(west, BorderLayout.WEST);
        getContentPane().add(scroll);



        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initOpeners(){
        openers = new JPanel(new GridLayout(2,3,5,5));
        next = new JButton("Open next");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //will be opening manuals from List<Manual>, so we reduce quantity to open by 1
                viewHandler.fireEventOpenNextManualsInBrowser((int)nextModel.getValue()-1);
            }
        });
        prev = new JButton("Open previous");
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewHandler.fireEventOpenPrevManualsInBrowser((int)prevModel.getValue()-1);
            }
        });

        nManuals = new JLabel("manuals");
        pManuals = new JLabel("manuals");

        nextModel = new SpinnerNumberModel(1,0,null,1);
        prevModel = new SpinnerNumberModel(1,0,null,1);
        nextSpinner = new JSpinner(nextModel);
        prevSpinner = new JSpinner(prevModel);

        openers.add(next);
        openers.add(nextSpinner);
        openers.add(nManuals);
        openers.add(prev);
        openers.add(prevSpinner);
        openers.add(pManuals);
    }


    public static void main(String[] args) {
        Console console = new Console();
        new MainView(console, null);
    }
}

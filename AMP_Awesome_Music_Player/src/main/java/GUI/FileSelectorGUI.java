package GUI;

import javax.swing.*;

public class FileSelectorGUI {

    private JPanel panel;
    private JFileChooser chooser;
    private JList<String> jlist;

    public FileSelectorGUI() {
        panel = new JPanel();

        setupJlist();
        setupChooser();

        panel.add(chooser);
        panel.add(jlist);
    }

    public JPanel getPanel() {
        return panel;
    }

    // todo: remove open and cancel buttons
    // todo: add file types in file chooser search

    private void setupJlist() {
        DefaultListModel<String> model = new DefaultListModel<>();
        jlist = new JList<>(model);
        jlist.setDragEnabled(true);
        jlist.setDropMode(DropMode.ON_OR_INSERT);
        jlist.setTransferHandler(new JlistTransferHandler());
        model.addElement("TEST");
    }

    private void setupChooser() {
        chooser = new JFileChooser();
        chooser.setDragEnabled(true);
    }

    class JlistTransferHandler extends TransferHandler {

        @Override
        public boolean importData(TransferSupport support) {
            System.out.println(support.getTransferable().toString());
            return super.importData(support);
        }

        @Override
        public boolean canImport(TransferSupport support) {
            return super.canImport(support);
        }

    }
}

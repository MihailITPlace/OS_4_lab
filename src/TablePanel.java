import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TablePanel extends JPanel {

    PhysicalMemoryTable pmt = PhysicalMemoryTable.getInstance();

    private int rowsCount;

    private int columnsCount;

    int sizeCell = 15;//this.getHeight() / 40;
   // private final int sizeCell = 15;

    public TablePanel(int rows, int columns) {
        rowsCount = rows;
        columnsCount = columns;
    }

    @Override
    protected void paintComponent(Graphics gh) {
        Graphics2D drp = (Graphics2D)gh;

        ArrayList<MemoryBlock> table = pmt.getTable();

        for (int i = 0; i < table.size(); i++) {
        int rowIndex = i / rowsCount;
        int columnIndex = i % rowsCount;

        int y = sizeCell * rowIndex;
        int x = sizeCell * columnIndex;

            drawCell(drp, x, y, table.get(i).getStatus());
        }

        /*int i = 32;

        int rowIndex = i / rowsCount;
        int columnIndex = i % rowsCount;

        int y = sizeCell * rowIndex;
        int x = sizeCell * columnIndex;

        drawCell(drp, x, y, table.get(i).getStatus());*/

        drawGrid(drp, sizeCell);
    }

    private void drawGrid(Graphics2D drp, int sizeCell) {
        drp.setColor(Color.BLACK);

        for (int i = 0; i < columnsCount; i++) {
            drp.drawLine(i * sizeCell, 0, i * sizeCell, rowsCount * sizeCell);
        }

        for (int i = 0; i < rowsCount; i++) {
            drp.drawLine(0, i * sizeCell, columnsCount * sizeCell,i * sizeCell);
        }
    }

    private void drawCell(Graphics2D drp, int x, int y, MemoryBlockStatus status) {
        switch (status) {
            case FREE:
                drp.setColor(new Color(238, 238, 238));
                break;
            case BUSY:
                drp.setColor(new Color(50, 117, 164));
                break;
            case SELECTED:
                drp.setColor(new Color(195, 158, 41));
        }

        drp.fillRect(x, y, sizeCell, sizeCell);
    }
}

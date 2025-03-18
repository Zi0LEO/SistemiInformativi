package com.opcal.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.List;

public class QueryResultsTable extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public QueryResultsTable() {
        setLayout(new BorderLayout());
    }

    /**
     * Initialize table with dynamic column names.
     *
     * @param columnNames Array of column names.
     */
    public void setColumnNames(String[] columnNames) {
        // Add the "Action" column to the column names dynamically
        String[] updatedColumnNames = new String[columnNames.length + 1];
        System.arraycopy(columnNames, 0, updatedColumnNames, 0, columnNames.length);
        updatedColumnNames[columnNames.length] = "Action"; // Add an "Action" column

        // Create a model with dynamic columns
        tableModel = new DefaultTableModel(updatedColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == tableModel.getColumnCount() - 1;
            }
        };

        // Init JTable with the model
        table = new JTable(tableModel);

        // Add custom button renderer and editor to the last column (Action column)
        ButtonRenderer buttonRenderer = new ButtonRenderer();
        ButtonEditor buttonEditor = new ButtonEditor(new JCheckBox()); // No checkbox needed, just a way to handle editing

        table.getColumn("Action").setCellRenderer(buttonRenderer); // Use the renderer for the "Action" column
        table.getColumn("Action").setCellEditor(buttonEditor);     // Use the editor for in-cell interaction

        // Add sorting functionality
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Repaint panel to reflect changes
        revalidate();
        repaint();
    }

    /**
     * Update table data dynamically with rows of data.
     *
     * @param rows List of rows where each row is an `Object[]`.
     */
    public void updateTableData(List<Object[]> rows) {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Add rows to the model (with a placeholder for the "Action" column)
        for (Object[] rowData : rows) {
            Object[] updatedRow = new Object[rowData.length + 1];
            System.arraycopy(rowData, 0, updatedRow, 0, rowData.length);
            updatedRow[rowData.length] = "Visualizza dettagli"; // Default button text
            tableModel.addRow(updatedRow);
        }
    }

    /**
     * Add click interactivity for column-based ordering.
     */
    public void enableSortByColumn() {
        table.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int col = table.columnAtPoint(e.getPoint());
            }
        });
    }

    /**
     * Renderer for the "Action" button in the table.
     */
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setText("Action");
        }

        @Override
        public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(UIManager.getColor("Button.background"));
            }
            setText(value != null ? value.toString() : "Action");
            return this;
        }
    }

    /**
     * Editor for the "Action" button in the table.
     */
    private class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private final JButton button;
        private String label;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            this.button = new JButton();
            this.button.setOpaque(true);
            this.button.addActionListener(this);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.label = value != null ? value.toString() : "Action";
            this.row = row; // Store the row, so we know which entry was clicked
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(
                QueryResultsTable.this,
                "Visualizza riga " + (row + 1) ,
                "Action",
                JOptionPane.INFORMATION_MESSAGE
            );
            fireEditingStopped();
        }

        @Override
        public boolean isCellEditable(EventObject anEvent) {
            return true; // Enable editing always in this column
        }
    }
}
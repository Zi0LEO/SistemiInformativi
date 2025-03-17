package com.opcal.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
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
        // Create a model with dynamic columns
        tableModel = new DefaultTableModel(columnNames, 0) {
            // Prevent editing to ensure data integrity
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Init JTable with the model
        table = new JTable(tableModel);

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

        // Add rows to the model
        for (Object[] rowData : rows) {
            tableModel.addRow(rowData);
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
                JOptionPane.showMessageDialog(
                    QueryResultsTable.this,
                    "Sorting by: " + table.getColumnName(col),
                    "Column Sorting",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }
}
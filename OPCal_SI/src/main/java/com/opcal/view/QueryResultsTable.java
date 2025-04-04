package com.opcal.view;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;

public class QueryResultsTable implements TableModelListener {
    private final JTable table;

    public QueryResultsTable() {
        table = new JTable(new DefaultTableModel());
        table.getModel().addTableModelListener(this);
        table.setAutoCreateRowSorter(true);
    }

    public JTable getTable() {
        return table;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        if (row < 0 || column < 0) {
            return;
        }
        TableModel model = (TableModel) e.getSource();
        String columnName = model.getColumnName(column);
        Object value = model.getValueAt(row, column);
    }

    public void setTableData(List<Object[]> data, List<String> columnNames) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(data.toArray(new Object[0][]), columnNames.toArray(new String[0]));
        model.fireTableStructureChanged();
    }

    public void search(String search) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(search));
    }
}
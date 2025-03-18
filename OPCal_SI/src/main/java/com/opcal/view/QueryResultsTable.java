package com.opcal.view;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
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

    public void setTableData(List<Object[]> data, String[] columnNames) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(data.toArray(new Object[0][]), columnNames);
        model.fireTableStructureChanged();
    }
}
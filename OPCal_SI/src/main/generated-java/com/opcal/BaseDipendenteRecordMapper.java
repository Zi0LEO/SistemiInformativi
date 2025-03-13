package com.opcal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.torque.Column;
import org.apache.torque.TorqueException;
import org.apache.torque.criteria.Criteria;
import org.apache.torque.om.mapper.RecordMapper;


/**
 * Maps ResultSet rows into Dipendente objects.
 *
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Thu Mar 13 14:45:23 CET 2025]
 *
 */
@SuppressWarnings("unused")
public class BaseDipendenteRecordMapper implements RecordMapper<Dipendente>
{
    /** Serial version */
    private static final long serialVersionUID = 1741873523077L;

    /** The class log. */
    private static Log log
            = LogFactory.getLog(BaseDipendenteRecordMapper.class);

    /**
     * Constructs the object from the current row in the resultSet.
     *
     * @param resultSet the resultSet to operate on, already pointing
     *        to the correct row. Not null.
     * @param offset a possible offset in the columns to be considered
     *        (if previous columns contain other objects),
     *        or 0 for no offset.
     * @param criteria The criteria which created the result set.
     *        If set, the attributes to set in the data object
     *        are determined from the select columns in the criteria;
     *        if no matching column can be found, null is returned.
     *        If not set, all of the table's columns are read from the
     *        result set in the order defined in the table definition.
     *
     * @return the mapped object, not null.
     *
     * @throws TorqueException when reading fields from the RecordSet fails
     *         or if a Criteria is passed which contains select columns other
     *         than the columns in the dipendente table.
     */
    public Dipendente processRow(
                ResultSet resultSet,
                int offset,
                Criteria criteria)
            throws TorqueException
    {
        Dipendente dipendente = new Dipendente();

        try 
        {
            dipendente.setLoading(true);
            if (criteria == null)
            {
                dipendente.setEmail(
                        getEmail(resultSet, offset + 1));
                dipendente.setPermessi(
                        getPermessi(resultSet, offset + 2));
            }
            else
            {
                // try to get columns to be mapped
                // from criteria's select columns
                boolean columnMapped = false;
                int totalOffset = offset + 1;
                List<Column> selectColumns = criteria.getSelectColumns();
                List<Column> columnsWithoutOffset = selectColumns.subList(
                        offset, 
                        selectColumns.size());
                for (Column column : columnsWithoutOffset)
                {
                    if (BaseDipendentePeer.EMAIL.getSqlExpression().equals(
                            column.getSqlExpression()))
                    {
                        dipendente.setEmail(
                            getEmail(resultSet, totalOffset));
                        columnMapped = true;
                    }
                    else if (BaseDipendentePeer.PERMESSI.getSqlExpression().equals(
                            column.getSqlExpression()))
                    {
                        dipendente.setPermessi(
                            getPermessi(resultSet, totalOffset));
                        columnMapped = true;
                    }
                    totalOffset++;
                }
                if (!columnMapped)
                {
                    log.debug("no columns to map found in criteria, "
                        + "returning null");
                    return null;
                }
            }
            dipendente.setNew(false);
            dipendente.setModified(false);
        }
        finally
        {
            dipendente.setLoading(false);
        }
        return dipendente;
    }

    /**
     * Reads the value of the <code>columnIndex</code>'th column
     * in the <code>resultSet</code> so that it can be used to set
     * the field email in Dipendente.
     *
     * @param resultSet the ResultSet to read from, not null.
     * @param columnIndex the index in the resultSet which should be read.
     *
     * @return the content of the column.
     *
     * @throws SQLException if a problem occurs when reading
     *         from the resultSet.
     */
    protected String getEmail(
                ResultSet resultSet,
                int columnIndex)
            throws TorqueException
    {
        try
        {
            return resultSet.getString(columnIndex);
        }
        catch (SQLException e)
        {
            throw new TorqueException(e);
        }
    }
    /**
     * Reads the value of the <code>columnIndex</code>'th column
     * in the <code>resultSet</code> so that it can be used to set
     * the field permessi in Dipendente.
     *
     * @param resultSet the ResultSet to read from, not null.
     * @param columnIndex the index in the resultSet which should be read.
     *
     * @return the content of the column.
     *
     * @throws SQLException if a problem occurs when reading
     *         from the resultSet.
     */
    protected int getPermessi(
                ResultSet resultSet,
                int columnIndex)
            throws TorqueException
    {
        try
        {
            return resultSet.getInt(columnIndex);
        }
        catch (SQLException e)
        {
            throw new TorqueException(e);
        }
    }

}

package com.opcal;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.torque.NoRowsException;
import org.apache.torque.TooManyRowsException;
import org.apache.torque.TorqueException;
import org.apache.torque.criteria.Criteria;
import org.apache.torque.om.mapper.RecordMapper;
import org.apache.torque.om.mapper.CompositeMapper;
import org.apache.torque.om.ObjectKey;
import org.apache.torque.om.SimpleKey;
import org.apache.torque.map.TableMap;
import org.apache.torque.util.TorqueConnection;
import org.apache.torque.util.Transaction;
import org.apache.torque.util.ColumnValues;
import org.apache.torque.util.JdbcTypedValue;



/**
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Thu Mar 13 14:45:22 CET 2025]
 *
 * You should not use this class directly.  It should not even be
 * extended; all references should be to DipendentePeer
 */

@SuppressWarnings("unused")
public abstract class BaseDipendentePeerImpl
    extends org.apache.torque.util.AbstractPeerImpl<Dipendente>
{
    /** Serial version */
    private static final long serialVersionUID = 1741873522759L;



    /**
     * Constructor.
     * The recordMapper, tableMap and databaseName fields are correctly
     * initialized.
     */
    public BaseDipendentePeerImpl()
    {
        this(new DipendenteRecordMapper(),
            DipendentePeer.TABLE,
            DipendentePeer.DATABASE_NAME);
    }

    /**
     * Constructor providing the objects to be injected as parameters.
     *
     * @param recordMapper a record mapper to map JDBC result sets to objects
     * @param tableMap the default table map
     * @param databaseName the name of the database
     */
    public BaseDipendentePeerImpl(
            RecordMapper<Dipendente> recordMapper, 
            TableMap tableMap,
            String databaseName)
    {
        super(recordMapper, tableMap, databaseName);
    }


    /**
     * Returns a new instance of the Data object class
     */
    public Dipendente getDbObjectInstance()
    {
        return new Dipendente();
    }


    /**
     * Method to do updates.  This method is to be used during a transaction,
     * otherwise use the doUpdate(Criteria) method.
     *
     * @param columnValues the values to update plus the primary key
     *        identifying the row to update.
     * @param con the connection to use, not null.
     *
     * @return the number of affected rows.
     *
     * @throws TorqueException Any exceptions caught during processing will be
     *         rethrown wrapped into a TorqueException.
     */
    public int doUpdate(ColumnValues columnValues, Connection con)
        throws TorqueException
    {
        Criteria selectCriteria = new Criteria(getDatabaseName());
        correctBooleans(columnValues);

        {
            JdbcTypedValue pkValue
                = columnValues.remove(DipendentePeer.EMAIL);
            if (pkValue == null)
            {
                throw new TorqueException(
                        "The value for the primary key column "
                        + "DipendentePeer.EMAIL"
                        + " must be set");
            }
            if (pkValue.getSqlExpression() == null)
            {
                selectCriteria.where(
                        DipendentePeer.EMAIL,
                        pkValue.getValue());
            }
            else
            {
                selectCriteria.where(
                        DipendentePeer.EMAIL,
                        pkValue.getSqlExpression());
            }
        }


        int rowCount = doUpdate(selectCriteria, columnValues, con);
        return rowCount;
    }

    /**
     * Deletes a data object, i.e. a row in a table, in the database.
     *
     * @param obj the data object to delete in the database, not null.
     *
     * @return the number of deleted rows.
     *
     * @throws TorqueException Any exceptions caught during processing will be
     *         rethrown wrapped into a TorqueException.
     */
    public int doDelete(Dipendente obj) throws TorqueException
    {
        int result = doDelete(buildCriteria(obj.getPrimaryKey()));
        obj.setDeleted(true);
        return result;
    }

    /**
     * Deletes a data object, i.e. a row in a table, in the database.
     * This method is to be used during a transaction, otherwise use the
     * doDelete(Dipendente) method.
     *
     * @param obj the data object to delete in the database, not null.
     * @param con the connection to use, not null.
     *
     * @return the number of deleted rows.
     *
     * @throws TorqueException Any exceptions caught during processing will be
     *         rethrown wrapped into a TorqueException.
     */
    public int doDelete(Dipendente obj, Connection con)
        throws TorqueException
    {
        int result = doDelete(buildCriteria(obj.getPrimaryKey()), con);
        obj.setDeleted(true);
        return result;
    }

    /**
     * Deletes data objects, i.e. rows in a table, in the database.
     *
     * @param objects the data object to delete in the database, not null,
     *        may not contain null.
     *
     * @return the number of deleted rows.
     *
     * @throws TorqueException Any exceptions caught during processing will be
     *         rethrown wrapped into a TorqueException.
     */
    public int doDelete(Collection<Dipendente> objects)
            throws TorqueException
    {
        int result = doDelete(buildPkCriteria(objects));
        objects.forEach(object -> object.setDeleted(true));
        return result;
    }

    /**
     * Deletes data objects, i.e. rows in a table, in the database.
     * This method uses the passed connection to delete the rows;
     * if a transaction is open in the connection, the deletion happens inside
     * this transaction.
     *
     * @param objects the data objects to delete in the database, not null,
     *        may not contain null.
     * @param con the connection to use for deleting, not null.
     *
     * @return the number of deleted rows.
     *
     * @throws TorqueException Any exceptions caught during processing will be
     *         rethrown wrapped into a TorqueException.
     */
    public int doDelete(
            Collection<Dipendente> objects,
            Connection con)
        throws TorqueException
    {
        int result = doDelete(buildPkCriteria(objects), con);
        objects.forEach(object -> object.setDeleted(true));
        return result;
    }

    /** 
     * Build a Criteria object which selects all objects which have a given
     * primary key.
     *
     * @param pk the primary key value to build the criteria from, not null.
     */
    public Criteria buildCriteria(ObjectKey<?> pk)
    {
        Criteria criteria = new Criteria();
        criteria.and(DipendentePeer.EMAIL, pk);
        return criteria;
     }

    /** 
     * Build a Criteria object which selects all objects which primary keys
     * are contained in the passed collection.
     *
     * @param pks the primary key values to build the criteria from, not null,
     *        may not contain null.
     */
    public Criteria buildCriteria(Collection<ObjectKey<?>> pks)
    {
        Criteria criteria = new Criteria();
        criteria.andIn(DipendentePeer.EMAIL, pks);
        return criteria;
     }


    /** 
     * Build a Criteria object which selects all passed objects using their
     * primary key. Objects which do not yet have a primary key are ignored.
     *
     * @param objects the objects to build the criteria from, not null,
     *        may not contain null.
     */
    public Criteria buildPkCriteria(
            Collection<Dipendente> objects)
    {
        return buildCriteria(objects.stream()
                .map(object -> object.getPrimaryKey())
                .collect(Collectors.toList()));
    }

    /** 
     * Build a Criteria object from the data object for this peer.
     * The primary key columns are only added if the object is not new.
     *
     * @param obj the object to build the criteria from, not null.
     */
    public Criteria buildCriteria(Dipendente obj)
    {
        Criteria criteria = new Criteria(getDatabaseName());
        if (!obj.isNew())
        {
            criteria.and(DipendentePeer.EMAIL, obj.getEmail());
        }
        criteria.and(DipendentePeer.PERMESSI, obj.getPermessi());
        return criteria;
    }

    /** 
     * Build a Criteria object from the data object for this peer,
     * skipping all binary columns.
     *
     * @param obj the object to build the criteria from, not null.
     */
    public Criteria buildSelectCriteria(Dipendente obj)
    {
        Criteria criteria = new Criteria(getDatabaseName());
        if (!obj.isNew())
        {
            criteria.and(DipendentePeer.EMAIL, obj.getEmail());
        }
        criteria.and(DipendentePeer.PERMESSI, obj.getPermessi());
        return criteria;
    }

    /** 
     * Returns the contents of the object as ColumnValues object.
     * Primary key columns which are generated on insertion are not
     * added to the returned object if they still have their initial
     * value. Also, columns which have the useDatabaseDefaultValue
     * flag set to true are also not added to the returned object
     * if they still have their initial value.
     *
     * @throws TorqueException if the table map cannot be retrieved
     *         (should not happen).
     */
    public ColumnValues buildColumnValues(Dipendente dipendente)
            throws TorqueException
    {
        ColumnValues columnValues = new ColumnValues();
        if (!dipendente.isNew() 
            || dipendente.getEmail() != null)
        {
            columnValues.put(
                    DipendentePeer.EMAIL,
                    new JdbcTypedValue(
                        dipendente.getEmail(),
                        12));
        }
        columnValues.put(
                DipendentePeer.PERMESSI,
                new JdbcTypedValue(
                    dipendente.getPermessi(),
                    4));
        return columnValues;
    }

    /**
     * Retrieve a single object by pk
     *
     * @param pk the primary key
     * @throws TorqueException Any exceptions caught during processing will be
     *         rethrown wrapped into a TorqueException.
     * @throws NoRowsException Primary key was not found in database.
     * @throws TooManyRowsException Primary key was not found in database.
     */
    public Dipendente retrieveByPK(String pk)
        throws TorqueException, NoRowsException, TooManyRowsException
    {
        return retrieveByPK(SimpleKey.keyFor(pk));
    }

    /**
     * Retrieve a single object by pk
     *
     * @param pk the primary key
     * @param con the connection to use
     * @throws TorqueException Any exceptions caught during processing will be
     *         rethrown wrapped into a TorqueException.
     * @throws NoRowsException Primary key was not found in database.
     * @throws TooManyRowsException Primary key was not found in database.
     */
    public Dipendente retrieveByPK(String pk, Connection con)
        throws TorqueException, NoRowsException, TooManyRowsException
    {
        return retrieveByPK(SimpleKey.keyFor(pk), con);
    }
    
    
    

    /**
     * Retrieve a single object by pk
     *
     * @param pk the primary key
     * @throws TorqueException Any exceptions caught during processing will be
     *         rethrown wrapped into a TorqueException.
     * @throws NoRowsException Primary key was not found in database.
     * @throws TooManyRowsException Primary key was not found in database.
     */
    public Dipendente retrieveByPK(ObjectKey<?> pk)
        throws TorqueException, NoRowsException, TooManyRowsException
    {
        try (TorqueConnection connection = Transaction.begin(getDatabaseName()))
        {
            Dipendente result = retrieveByPK(pk, connection);
            Transaction.commit(connection);
            return result;
        }
    }

    /**
     * Retrieve a single object by pk
     *
     * @param pk the primary key
     * @param con the connection to use
     * @throws TorqueException Any exceptions caught during processing will be
     *         rethrown wrapped into a TorqueException.
     * @throws NoRowsException Primary key was not found in database.
     * @throws TooManyRowsException Primary key was not found in database.
     */
    public Dipendente retrieveByPK(ObjectKey<?> pk, Connection con)
        throws TorqueException, NoRowsException, TooManyRowsException
    {
        Criteria criteria = buildCriteria(pk);
        Dipendente v = doSelectSingleRecord(criteria, con);
        if (v == null)
        {
            throw new NoRowsException("Failed to select a row.");
        }

        return v;
    }


    /**
     * Retrieve multiple objects by pk.
     *
     * @param pks List of primary keys.
     *        Entries in pks which do not match entries in the database are ignored.
     *
     * @return the list of matching objects, not null.
     *
     * @throws TorqueException Any exceptions caught during processing will be
     *         rethrown wrapped into a TorqueException.
     */
    public List<Dipendente> retrieveByTypedPKs(Collection<String> pks)
        throws TorqueException
    {
        try (TorqueConnection connection = Transaction.begin(getDatabaseName()))
        {
            List<Dipendente> result = retrieveByTypedPKs(pks, connection);
            Transaction.commit(connection);
            return result;
        }
    }

    /**
     * Retrieve multiple objects by pk.
     *
     * @param pks List of primary keys.
     *        Entries in pks which do not match entries in the database are ignored.
     * @param dbcon the connection to use
     *
     * @return the list of matching objects, not null.
     *
     * @throws TorqueException Any exceptions caught during processing will be
     *         rethrown wrapped into a TorqueException.
     */
    public List<Dipendente> retrieveByTypedPKs(
                Collection<String> pks,
                Connection dbcon)
            throws TorqueException
    {
        if (pks == null || pks.size() == 0)
        {
            return new ArrayList<Dipendente>();
        }
        List<ObjectKey<?>> objectKeyList = new ArrayList<ObjectKey<?>>();
        for (String pk : pks)
        {
            objectKeyList.add(SimpleKey.keyFor(pk));
        }
        Criteria criteria = buildCriteria(objectKeyList);
        List<Dipendente> result = doSelect(criteria, dbcon);
        return result;
    }

    /**
     * Retrieve multiple objects by pk.
     *
     * @param pks List of primary keys.
     *        Entries in pks which do not match entries in the database are ignored.
     *
     * @return the list of matching objects, not null.
     *
     * @throws TorqueException Any exceptions caught during processing will be
     *         rethrown wrapped into a TorqueException.
     */
    public List<Dipendente> retrieveByObjectKeys(Collection<ObjectKey<?>> pks)
        throws TorqueException
    {
        try (TorqueConnection connection = Transaction.begin(getDatabaseName()))
        {
            List<Dipendente> result = retrieveByObjectKeys(pks, connection);
            Transaction.commit(connection);
            return result;
        }
    }

    /**
     * Retrieve multiple objects by pk.
     *
     * @param pks List of primary keys.
     *        Entries in pks which do not match entries in the database are ignored.
     * @param dbcon the connection to use
     *
     * @return the list of matching objects, not null.
     *
     * @throws TorqueException Any exceptions caught during processing will be
     *         rethrown wrapped into a TorqueException.
     */
    public List<Dipendente> retrieveByObjectKeys(
                Collection<ObjectKey<?>> pks,
                Connection dbcon)
            throws TorqueException
    {
        if (pks == null || pks.size() == 0)
        {
            return new ArrayList<Dipendente>();
        }
        Criteria criteria = buildCriteria(pks);
        List<Dipendente> result = doSelect(criteria, dbcon);
        return result;
    }


    /**
     * Selects a collection of dbObjectClassName objects pre-filled with their
     * Utente objects.
     *
     * @throws TorqueException Any exceptions caught during processing will be
     *         rethrown wrapped into a TorqueException.
     */
    public List<Dipendente> doSelectJoinUtente(Criteria criteria)
        throws TorqueException
    {
        try (TorqueConnection connection = Transaction.begin(getDatabaseName()))
        {
            List<Dipendente> result
                    = DipendentePeer.doSelectJoinUtente(criteria, connection);
            Transaction.commit(connection);
            return result;
        }
    }

    /**
     * Selects a collection of Dipendente objects pre-filled with their
     * Utente objects.
     *
     * @throws TorqueException Any exceptions caught during processing will be
     *         rethrown wrapped into a TorqueException.
     */
    public List<Dipendente> doSelectJoinUtente(
            Criteria criteria, Connection conn)
        throws TorqueException
    {
        setDbName(criteria);

        addSelectColumns(criteria);
        com.opcal.UtentePeer.addSelectColumns(criteria);

        CompositeMapper compositeMapper = new CompositeMapper();
        compositeMapper.addMapper(
                new DipendenteRecordMapper(),
                0);
        compositeMapper.addMapper(
                new UtenteRecordMapper(),
                DipendentePeer.numColumns);
        criteria.addJoin(DipendentePeer.EMAIL,
            com.opcal.UtentePeer.EMAIL);

        correctBooleans(criteria);

        List<Dipendente> result = new ArrayList<Dipendente>();
        List<List<Object>> rawResult = doSelect(
                criteria, compositeMapper, conn);
        for (List<Object> rawResultRow : rawResult)
        {
            Dipendente obj1 = (Dipendente) rawResultRow.get(0);
            Utente obj2 = (Utente) rawResultRow.get(1);

            boolean newObject = true;
            for (int j = 0; j < result.size(); j++)
            {
                Dipendente temp_obj1 = result.get(j);
                com.opcal.Utente temp_obj2 = temp_obj1.getUtente();
                if (temp_obj2.getPrimaryKey().equals(obj2.getPrimaryKey()))
                {
                    newObject = false;
                    temp_obj2.addDipendente(obj1);
                    break;
                }
            }
            if (newObject)
            {
                obj2.initDipendentes();
                obj2.addDipendente(obj1);
            }
            result.add(obj1);
        }
        return result;
    }




}

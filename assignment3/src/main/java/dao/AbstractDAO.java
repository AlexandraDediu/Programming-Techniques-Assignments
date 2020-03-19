package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.ConnectionFactory;

public class AbstractDAO<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    public T findById(int id, String field) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(field);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
            
        } catch(IndexOutOfBoundsException e) {
        	LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        	throw new IllegalArgumentException("Invalid product/customer id: " + id);
        }
        
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private String createSelectAllStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    public List<T> selectAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllStatement();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        try {
            while (resultSet.next()) {
                T instance = type.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String createInsertStatement(Field[] fields) {
        StringBuilder sb = new StringBuilder();
        sb.append(" INSERT INTO ");
        sb.append(type.getSimpleName() + "(");
        String prefix = "";
        for (Field f : fields) {
            if (f.getName().equals("id")) {
                continue;
            }
            sb.append(prefix);
            prefix = ", ";
            sb.append(f.getName());
        }
        sb.append(") VALUES (");
        // id should auto_increment, no need to consider it when inserting
        for (int i = 0; i < fields.length - 2; i++)
            sb.append("? ,");
        sb.append("?) ");
        return sb.toString();
    }

    public void insert(T element) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Field[] fields = type.getDeclaredFields();
        String query = createInsertStatement(fields);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i = 1;
            for (Field f : fields) {
                if(f.getName().equals("id")) continue;

                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(f.getName(), type);
                Method method = propertyDescriptor.getReadMethod(); //Gets the method that should be used to read the property value
                Object fieldValue = method.invoke(element);
                if (fieldValue instanceof Integer) {
                    statement.setInt(i, (Integer) fieldValue);
                } else if (fieldValue instanceof Float) {
                    statement.setFloat(i, (Float) fieldValue);
                } else {
                    statement.setString(i, (String) fieldValue);
                }
                i++;
            }
            statement.execute();

        } catch(SQLIntegrityConstraintViolationException e) {
        	LOGGER.log(Level.WARNING, "DAO:insert " + e.getMessage());
        	throw new IllegalArgumentException("Invalid reference to another objectId");
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "DAO:insert " + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

    }

    private String createUpdateStatement(Field[] fields, String searchField) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for (int i=0; i<fields.length-1; i++) {
        	Field f = fields[i];
            if (f.getName().equals("id")) {
                continue;
            }
            sb.append(f.getName());
            sb.append("= ?,");
            
        }
        sb.append(fields[fields.length -1].getName());
        sb.append("= ?");
        sb.append(" WHERE ");
        sb.append(searchField);
        sb.append("= ?");
        System.out.println(sb.toString());
        return sb.toString();
    }

    /* */

    public void update(T element) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Field[] fields = type.getDeclaredFields();
        String query = createUpdateStatement(fields, "id");
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i = 1;
            int id = -1;
            for(Field f: fields) {

                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(f.getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                Object fieldValue = method.invoke(element);
                //save id value as it will be used in the end of the statement
                if(f.getName() == "id") {
                    id = (Integer) fieldValue;
                    continue;
                }
                if (fieldValue instanceof Integer) {
                    statement.setInt(i, (Integer) fieldValue);
                } else if (fieldValue instanceof Float) {
                    statement.setFloat(i, (Float) fieldValue);
                } else {
                    statement.setString(i, (String) fieldValue);
                }
                i++;
            }
            statement.setInt(i, id);
            statement.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "DAO:update " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public String createDeleteStatement(String searchFieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        sb.append(searchFieldName);
        sb.append("= ?");
        return sb.toString();
    }

    public void delete(T element) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createDeleteStatement("id");
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
           
            Field[] fields = type.getDeclaredFields();
            for(Field f: fields) {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(f.getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                Object fieldValue = method.invoke(element);
                //save id value as it will be used in the end of the statement
                if(f.getName() == "id") {
                    statement.setInt(1, (Integer) fieldValue);
                    break;
                }
            }
            statement.execute();
            
        }catch(SQLIntegrityConstraintViolationException e){
        	throw new IllegalArgumentException("Cannot delete object as it is part of order");
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "DAO:delete " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}  

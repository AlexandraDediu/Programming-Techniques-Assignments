package presentation.views.add;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public abstract class AbstractAddView<T> {

    private final Class<T> type;

    protected JFrame addView;

    @SuppressWarnings("unchecked")
    public AbstractAddView() {

        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        addView = new JFrame("Add " + type.getSimpleName());
    }

    public void drawView() {
        try {

            addView.setSize(400, 800);

            int i = 0;
            final List<JTextField> inputs = new ArrayList<JTextField>();
            for (Field field : type.getDeclaredFields()) { //
                if (field.getName().equals("id")) {
                    continue;
                }
                JLabel label = new JLabel(field.getName());
                label.setBounds(100, 100 + i * 30, 150, 30);
                JTextField textField = new JTextField(50);
                textField.setBounds(200, 100 + i * 30, 150, 30);
                textField.setName(field.getName());
                inputs.add(textField);

                addView.add(label);
                addView.add(textField);
                i++;
            }
            JButton save = new JButton("Save");
            save.setBounds(200, 100 + i * 30, 150, 30);
            addView.add(save);
            save.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    try {
                        T instance = type.newInstance();

                        for (JTextField textField : inputs) {
                            if (((JTextField) textField).getText().isEmpty()) {
                                JOptionPane.showMessageDialog(addView, textField.getName() + " should not be empty");
                                return;
                            }
                            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(textField.getName(), type);
                            Method writeMethod = propertyDescriptor.getWriteMethod();
                            writeMethod.invoke(instance,
                                    toObject(propertyDescriptor.getPropertyType(), textField.getText()));
                        }
                        insert(instance);
                        addView.dispatchEvent(new WindowEvent(addView, WindowEvent.WINDOW_CLOSING));
                    } catch (IllegalArgumentException iae) {
                        JOptionPane.showMessageDialog(addView, iae.getMessage());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            addView.setSize(700, 500);
            addView.setLayout(null);// using no layout managers
            addView.setVisible(true);// making the frame visible
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void insert(T object);

    public static Object toObject(Class clazz, String value) {
        if (Boolean.class == clazz)
            return Boolean.parseBoolean(value);
        if (Byte.class == clazz)
            return Byte.parseByte(value);
        if (Short.class == clazz)
            return Short.parseShort(value);
        if (Integer.class == clazz)
            return Integer.parseInt(value);
        if (Long.class == clazz)
            return Long.parseLong(value);
        if (Float.class == clazz)
            return Float.parseFloat(value);
        if (Double.class == clazz)
            return Double.parseDouble(value);
        return value;
    }
}

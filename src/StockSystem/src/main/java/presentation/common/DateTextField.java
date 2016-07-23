package presentation.common;

import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextField;

import com.qt.datapicker.DatePicker;



public class DateTextField extends JTextField implements Observer {

	private String parrten;

	private static final long serialVersionUID = -5454499442988399584L;

	public DateTextField(String pattern) {
		this.parrten = pattern;
	}

	@Override
	public void update(Observable o, Object arg) {
		Calendar calendar = (Calendar) arg;
		DatePicker datePicker = (DatePicker) o;
		setText(datePicker.formatDate(calendar, parrten));
	}

}

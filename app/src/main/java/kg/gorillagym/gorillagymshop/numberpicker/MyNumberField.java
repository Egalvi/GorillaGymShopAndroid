package kg.gorillagym.gorillagymshop.numberpicker;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kg.gorillagym.gorillagymshop.R;

public class MyNumberField extends View {

    public MyNumberField(Context context) {
        super(context);
    }

    public MyNumberField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNumberField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.number_field);
        EditText value = getValueEdit();
        Button plusBtn = getPlusButton();
        Button minusBtn = getMinusButton();
    }

    private Button getMinusButton() {
        return (Button) findViewById(R.id.minus_button);
    }

    private Button getPlusButton() {
        return (Button) findViewById(R.id.plus_button);
    }

    private EditText getValueEdit() {
        return (EditText) findViewById(R.id.int_value);
    }

    public void setWidth(int width) {
        getValueEdit().setWidth(width);
        getPlusButton().setWidth(width);
        getMinusButton().setWidth(width);
    }

    public void setHeight(int height) {
        getValueEdit().setHeight(height / 3);
        getPlusButton().setHeight(height / 3);
        getMinusButton().setHeight(height / 3);
    }

    public void setValue(int value){
//        getValueEdit().setText(value);
    }
}

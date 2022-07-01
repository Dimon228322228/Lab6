package utilites;

import java.awt.*;

public class MyConstraints extends GridBagConstraints {

    public MyConstraints(){
        initDefault();
    }

    public void initDefault(){
        gridx = 0;
        gridy = 0;
        gridwidth = 1;
        gridheight = 1;
        weightx = 0;
        weighty = 0;
        anchor = EAST;
        fill = HORIZONTAL;
        insets = new Insets(2,2,2,2);
        ipadx = 0;
        ipady = 0;
    }

    public MyConstraints setGridX(int x){
        gridx = x;
        return this;
    }

    public MyConstraints setGridY(int y){
        gridy = y;
        return this;
    }

    public MyConstraints setGridWidth(int width){
        gridwidth = width;
        return this;
    }

    public MyConstraints setGridHeight(int height){
        gridheight = height;
        return this;
    }

    public MyConstraints setWeightX(int weight){
        weightx = weight;
        return this;
    }

    public MyConstraints setWeightY(int weight){
        weighty = weight;
        return this;
    }

    public MyConstraints setAnchor(int magicConstant){
        anchor = magicConstant;
        return this;
    }

    public MyConstraints setFill(int magicConstant){
        fill = magicConstant;
        return this;
    }

    public MyConstraints setInsets(Insets insets){
        this.insets = insets;
        return this;
    }

    public MyConstraints setLeftInsets(int leftInsets){
        insets = new Insets(insets.top, leftInsets, insets.bottom, insets.right);
        return this;
    }

    public MyConstraints setTopInsets(int topInsets){
        insets = new Insets(topInsets, insets.left, insets.bottom, insets.right);
        return this;
    }

    public MyConstraints setBottomInsets(int bottomInsets){
        insets = new Insets(insets.top, insets.left, bottomInsets, insets.right);
        return this;
    }

    public MyConstraints setRightInsets(int rightInsets){
        insets = new Insets(insets.top, insets.left, insets.bottom, rightInsets);
        return this;
    }

    public MyConstraints setIpadX(int x){
        ipadx = x;
        return this;
    }

    public MyConstraints setIpadY(int y){
        ipady = y;
        return this;
    }
}

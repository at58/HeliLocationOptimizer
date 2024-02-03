package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Button extends JButton {

  public Button(String label,
                Color background,
                Color foreGround,
                Font font,
                String toolTip,
                Point coordinate,
                Dimension dimension,
                ActionListener listener) {

    super(label);
    setBackground(background);
    setForeground(foreGround);
    setFont(font.getFont());
    setToolTipText(toolTip);
    setBounds(coordinate.x, coordinate.y, dimension.width, dimension.height);
    addActionListener(listener);
  }

  public void offerActionListener(ActionListener listener) {
    addActionListener(listener);
  }

  public JButton getJButton() {
    return this;
  }
}
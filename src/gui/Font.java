package gui;

/**
 * This class declares some font configurations for the gui font style.
 */
public enum Font {

  CONSOLAS14(new java.awt.Font("Consolas", java.awt.Font.BOLD, 14)),
  CONSOLAS16(new java.awt.Font("Consolas", java.awt.Font.BOLD, 16)),
  CONSOLAS18(new java.awt.Font("Consolas", java.awt.Font.BOLD, 18)),
  CONSOLAS20(new java.awt.Font("Consolas", java.awt.Font.BOLD, 20)),
  CONSOLAS22(new java.awt.Font("Consolas", java.awt.Font.BOLD, 22));

  private final java.awt.Font font;

  Font(java.awt.Font font) {
    this.font = font;
  }

  public java.awt.Font getFont() {
    return this.font;
  }
}

package domain.visualization;

import domain.Enum.Team;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ConsolePanel extends JPanel {

    //출력창
    private JTextArea consoleOutput;
    private JScrollPane consoleScrollPane;
    //입력창
    public JTextField consoleInput;
    public JPanel inputPanel;


    public ConsolePanel() {

        setLayout(new BorderLayout());

        // 콘솔 출력 영역
        createConsoleOutput();
        // 콘솔 입력 영역
        createConsoleInput ();


        // 출력 영역과 입력 영역을 함께 담는 콘솔 패널
        add(consoleScrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void createConsoleOutput(){
        consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
        consoleOutput.setFont(new Font("Monospaced", Font.PLAIN, 14));
        consoleOutput.setLineWrap(true);
        consoleOutput.setWrapStyleWord(true);
        consoleOutput.setBackground(Color.WHITE);
        consoleOutput.setForeground(Color.BLACK);
        consoleOutput.setMargin(new Insets(5, 5, 5, 5));
        consoleScrollPane = new JScrollPane(consoleOutput);

        // TitledBorder 스타일
        TitledBorder outputBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Console Output",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Monospaced", Font.BOLD, 14), Color.BLACK);
        consoleScrollPane.setBorder(outputBorder);
    }

    private void createConsoleInput (){
        consoleInput = new JTextField();
        consoleInput.setFont(new Font("Monospaced", Font.PLAIN, 14));
        consoleInput.setBackground(Color.WHITE);
        consoleInput.setForeground(Color.BLACK);
        consoleInput.setCaretColor(Color.BLACK);
        consoleInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(consoleInput, BorderLayout.CENTER);

        // 입력 영역을 담을 패널
        TitledBorder inputBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Input Command",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Monospaced", Font.BOLD, 14), Color.BLACK);
        inputPanel.setBorder(inputBorder);
    }
    // 콘솔 출력 영역에 텍스트 추가
    public void appendToConsole(String text) {
        consoleOutput.append(text);
        consoleOutput.setCaretPosition(consoleOutput.getDocument().getLength());
    }
    public void printChangeTurn (Team turn){
        //appendToConsole ($"{turn} 차례입니다. \n ");
        appendToConsole(String.format("\n%s 차례입니다 \n다음과 같은 형식으로 명령을 입력해주세요 ex(1a 2b)", turn));
    }
    public boolean isCommandValid(String consoleText) {

        if (consoleText == null || consoleText.trim().isEmpty()) {
            return false;
        }

        return consoleText.matches("^[1-8][a-h] [1-8][a-h]$");
    }

    public void initializeConsoleInput(){
        consoleInput.setText("");
    }
    public String getConsoleText () {
        return consoleInput.getText();
    }
}

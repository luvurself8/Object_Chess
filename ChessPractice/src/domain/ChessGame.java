package domain;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class ChessGame  extends JFrame {
    private final int BOARD_SIZE = 8;
    private JPanel boardPanel;
    private JLabel[][] boardSquares;

    private JTextArea consoleOutput;
    private JTextField consoleInput;
    private JPanel consolePanel;

    public ChessGame() {
        setTitle("Go Chess");
        // 프레임 전체 크기를 넓게 설정 (체스판 700 + 콘솔 영역 넓게)
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 체스판과 기물 초기화
        initializeBoard();
        initializePieces();

        // 콘솔(입출력) 초기화
        initializeConsole();

        // JSplitPane을 사용하여 왼쪽에 체스판, 오른쪽에 콘솔 배치
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, boardPanel, consolePanel);
        // 체스판 영역의 크기를 고정: 700x700
        boardPanel.setPreferredSize(new Dimension(700, 700));
        boardPanel.setMinimumSize(new Dimension(700, 700));
        // divider를 700px로 고정하여 콘솔 영역이 넓어짐
        splitPane.setDividerLocation(700);
        splitPane.setResizeWeight(0.0);
        add(splitPane, BorderLayout.CENTER);

        setVisible(true);
    }

    // 10x10 그리드: 내부 8x8은 체스판, 가장자리 1칸씩은 좌표 표시용
    private void initializeBoard() {
        int gridSize = BOARD_SIZE + 2; // 10x10 그리드
        boardPanel = new JPanel(new GridLayout(gridSize, gridSize));
        boardSquares = new JLabel[BOARD_SIZE][BOARD_SIZE];

        // 각 셀을 순서대로 추가
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                // 가장자리 셀: 좌표 표시용
                if (row == 0 || row == gridSize - 1 || col == 0 || col == gridSize - 1) {
                    JLabel label = new JLabel("", SwingConstants.CENTER);
                    label.setFont(new Font("SansSerif", Font.BOLD, 16));
                    // 상단/하단 (모서리 제외): 열 좌표 (소문자 a ~ h)
                    if ((row == 0 || row == gridSize - 1) && col != 0 && col != gridSize - 1) {
                        label.setText(String.valueOf((char)('a' + col - 1)));
                    }
                    // 좌측/우측 (모서리 제외): 행 좌표 (8 ~ 1)
                    if ((col == 0 || col == gridSize - 1) && row != 0 && row != gridSize - 1) {
                        label.setText(String.valueOf(BOARD_SIZE - (row - 1)));
                    }
                    boardPanel.add(label);
                } else {
                    // 실제 체스판 셀 (8x8)
                    int boardRow = row - 1;
                    int boardCol = col - 1;
                    boardSquares[boardRow][boardCol] = new JLabel("", SwingConstants.CENTER);
                    boardSquares[boardRow][boardCol].setOpaque(true);
                    boardSquares[boardRow][boardCol].setFont(new Font("SansSerif", Font.PLAIN, 48));
                    // 배경색 설정: (행+열)이 짝수이면 회색, 홀수이면 흰색
                    if ((boardRow + boardCol) % 2 == 0) {
                        boardSquares[boardRow][boardCol].setBackground(Color.GRAY);
                    } else {
                        boardSquares[boardRow][boardCol].setBackground(Color.WHITE);
                    }
                    boardSquares[boardRow][boardCol].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    boardPanel.add(boardSquares[boardRow][boardCol]);
                }
            }
        }
    }

    // 체스 기물을 초기 위치에 배치 (상단: 검은색, 하단: 흰색)
    private void initializePieces() {
        // 검은색 기물 배치 (체스판 최상단 행: boardSquares[0]는 8번 행)
        boardSquares[0][0].setText("\u265C"); // Rook
        boardSquares[0][1].setText("\u265E"); // Knight
        boardSquares[0][2].setText("\u265D"); // Bishop
        boardSquares[0][3].setText("\u265B"); // Queen
        boardSquares[0][4].setText("\u265A"); // King
        boardSquares[0][5].setText("\u265D"); // Bishop
        boardSquares[0][6].setText("\u265E"); // Knight
        boardSquares[0][7].setText("\u265C"); // Rook
        for (int col = 0; col < BOARD_SIZE; col++) {
            boardSquares[1][col].setText("\u265F"); // Pawn
        }

        // 흰색 기물 배치 (체스판 최하단 행: boardSquares[7]는 1번 행)
        boardSquares[7][0].setText("\u2656"); // Rook
        boardSquares[7][1].setText("\u2658"); // Knight
        boardSquares[7][2].setText("\u2657"); // Bishop
        boardSquares[7][3].setText("\u2655"); // Queen
        boardSquares[7][4].setText("\u2654"); // King
        boardSquares[7][5].setText("\u2657"); // Bishop
        boardSquares[7][6].setText("\u2658"); // Knight
        boardSquares[7][7].setText("\u2656"); // Rook
        for (int col = 0; col < BOARD_SIZE; col++) {
            boardSquares[6][col].setText("\u2659"); // Pawn
        }
    }

    // 콘솔 패널(입력/출력) 초기화: 오른쪽에 CLI 역할 수행
    private void initializeConsole() {
        // 콘솔 출력 영역
        consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
        consoleOutput.setFont(new Font("Monospaced", Font.PLAIN, 14));
        consoleOutput.setLineWrap(true);
        consoleOutput.setWrapStyleWord(true);
        // 배경색과 글자색: 흰색 배경, 검정색 글자
        consoleOutput.setBackground(Color.WHITE);
        consoleOutput.setForeground(Color.BLACK);
        consoleOutput.setMargin(new Insets(5, 5, 5, 5));
        JScrollPane consoleScrollPane = new JScrollPane(consoleOutput);
        // TitledBorder 스타일 통일
        TitledBorder outputBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Console Output",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Monospaced", Font.BOLD, 14), Color.BLACK);
        consoleScrollPane.setBorder(outputBorder);

        // 콘솔 입력 영역
        consoleInput = new JTextField();
        consoleInput.setFont(new Font("Monospaced", Font.PLAIN, 14));
        // 배경색과 글자색: 흰색 배경, 검정색 글자
        consoleInput.setBackground(Color.WHITE);
        consoleInput.setForeground(Color.BLACK);
        consoleInput.setCaretColor(Color.BLACK);
        consoleInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        consoleInput.addActionListener(e -> {
            String input = consoleInput.getText();
            appendToConsole(">> " + input + "\n");
            processCommand(input);
            consoleInput.setText("");
        });

        // 입력 영역을 담을 패널
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(consoleInput, BorderLayout.CENTER);
        // TitledBorder 스타일 통일
        TitledBorder inputBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Input Command",
                TitledBorder.LEADING, TitledBorder.TOP, new Font("Monospaced", Font.BOLD, 14), Color.BLACK);
        inputPanel.setBorder(inputBorder);

        // 출력 영역과 입력 영역을 함께 담는 콘솔 패널
        consolePanel = new JPanel(new BorderLayout());
        consolePanel.add(consoleScrollPane, BorderLayout.CENTER);
        consolePanel.add(inputPanel, BorderLayout.SOUTH);
    }

    // 콘솔 출력 영역에 텍스트 추가
    private void appendToConsole(String text) {
        consoleOutput.append(text);
        consoleOutput.setCaretPosition(consoleOutput.getDocument().getLength());
    }

    // CLI 명령 처리 (현재는 단순 에코)
    private void processCommand(String input) {
        appendToConsole("You entered: " + input + "\n");
    }

    public static void main(String[] args) {
        //SwingUtilities.invokeLater(() -> new ChessGame());
        System.out.println( Team.BLACK);
    }
}

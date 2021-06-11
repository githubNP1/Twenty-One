package Twenty_One;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

public class Main extends JPanel implements ActionListener{
    ArrayList<Card> deck = new ArrayList<>();
    ArrayList<Card> fullDeck = new ArrayList<>();
    BufferedImage cards;
    BufferedImage table;
    //int[] card;
    ArrayList<Card> dealerCards = new ArrayList<>();
    ArrayList<Card> playerFirstCards = new ArrayList<>();
    ArrayList<Card> playerSecondCards = new ArrayList<>();
    
    JButton hit, stand, Double, split, Bet, Chip1, Chip5, Chip10, Chip25, Chip50, Chip100, Chip500, Chip1000;
    JTextArea display;
    JTextField entry;
    JLabel labelBet;
    JLabel labelChips;
    JLabel chips;
    
    Boolean readyToBet = false;
    int bet;
    
    Boolean readyToMove = false;
    int hand;
    
    Player player;
    Dealer dealer;
    
    ArrayList<JButton> moveButtons;
    ArrayList<JButton> chipButtons;
    
    public Main() throws IOException{
        JFrame frame = new JFrame();
        frame.add(this);
        frame.setSize(1020, 750); 
        setupScreen();
        frame.setVisible(true);
        
        player = new Player(10000);
        dealer = new Dealer();
        
        getDeck();
        getCardImages();
        run(player, dealer);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) throws IOException{ 
    //play runs out when chips greater than 1 - ok, but if no chips left, can still double - will go into minus chips
        Main game = new Main();
        
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g); 
        g.drawImage(table, 1, 1, null);
        try{
            int i = 0;
            for(Card card : dealerCards){
                g.drawImage(cards, 400 + i*72, 70, 472 + i*72, 166 + i, card.x1, card.y1, card.x2, card.y2, null);
                i++;
            }
            int j = 0;
            for(Card card : playerFirstCards){
                g.drawImage(cards, 80 + i*72, 400, 152 + i*72, 496 + i, card.x1, card.y1, card.x2, card.y2, null);
                j++;
            }
            int k = 0;
            for(Card card : playerSecondCards){
                g.drawImage(cards, 400 + i*72, 400, 472 + i*72, 496 + i, card.x1, card.y1, card.x2, card.y2, null);
                k++;
            }
        }catch(Exception e){}
    }
    
    public void getCardImages(){
        try {
            File input = new File("cards.jpg");
            cards = ImageIO.read(input);
            File input2 = new File("blackjack table.jpg");
            table = ImageIO.read(input2);
        }
        catch (Exception e) {System.out.println(e);}
        //card = new int[]{fullDeck.get(17).x1, fullDeck.get(17).y1, fullDeck.get(17).x2, fullDeck.get(17).y2};
        //repaint();
    }
    
    public void setupScreen(){ //could be condensed?
        this.setLayout(null); 
        moveButtons = new ArrayList<>();
        chipButtons = new ArrayList<>();      
        
        hit = createButtons(hit, "hit.jpg", 80, 480, 70, 40, false, moveButtons);
        stand = createButtons(stand, "stand.jpg", 200, 480, 137, 38, false, moveButtons);
        Double = createButtons(Double, "double.jpg", 400, 480, 170, 40, false, moveButtons);
        split = createButtons(split, "split.jpg", 600, 480, 114, 40, false, moveButtons);
        Bet = createButtons(Bet, "bet.jpg", 120, 80, 132, 68, true, chipButtons);
        Chip1 = createButtons(Chip1, "1 chip.jpg", 30, 570, 114, 114, true, chipButtons);
        Chip5 = createButtons(Chip5, "5 chip.jpg", 150, 570, 113, 116, true, chipButtons);
        Chip10 = createButtons(Chip10, "10 chip.jpg", 270, 570, 114, 116, true, chipButtons);
        Chip25 = createButtons(Chip25, "25 chip.jpg", 390, 570, 113, 114, true, chipButtons);
        Chip50 = createButtons(Chip50, "50 chip.jpg", 510, 570, 114, 114, true, chipButtons);
        Chip100 = createButtons(Chip100, "100 chip.jpg", 630, 570, 114, 114, true, chipButtons);
        Chip500 = createButtons(Chip500, "500 chip.jpg", 750, 570, 116, 116, true, chipButtons);
        Chip1000 = createButtons(Chip1000, "1000 chip.jpg", 870, 570, 114, 116, true, chipButtons);
        
        labelBet = createLabels(labelBet, 880, 500, 60, 20);
        labelChips = createLabels(labelChips, 880, 520, 100, 20);
        
        display = new JTextArea(5,10); display.setLineWrap(true); display.setWrapStyleWord(true);
        display.setBounds(700, 200, 100, 50); 
        entry = new JTextField(10);
        entry.setBounds(700, 300, 50, 20);
        this.add(display); 
        this.add(entry); 
        
        //repaint(); //is this necessary
    }
    
    public JButton createButtons(JButton button, String file, int x, int y, int width, int height, Boolean visible, ArrayList<JButton> group){ //creates buttons, uses file as imageicon, set bounds
        ImageIcon image = new ImageIcon(file);
        button = new JButton(image);
        button.setBounds(x, y, width, height); 
        button.setVisible(visible);
        group.add(button);
        this.add(button);
        button.addActionListener(this);
        return button;
    }
    
    public JLabel createLabels(JLabel label, int x, int y, int width, int height){
        label = new JLabel(); 
        label.setBounds(x, y, width, height); 
        this.add(label);
        return label;
    }
    
    public void changeButtonVisibility(ArrayList<JButton> list, Boolean visibile){
        for(JButton button : list){
            button.setVisible(visibile);
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == hit && readyToMove){
            hit(player.hands.get(hand), true); 
            readyToMove = false;
        }
        else if(e.getSource() == stand){
            
        }
        else if (e.getSource() == Double){
            
        }
        else if(e.getSource() == split){
            
        }
        else if (e.getSource() == Chip1){
            bet += 1;
        }
        else if (e.getSource() == Chip5){
            bet += 5;
        }
        else if (e.getSource() == Chip10){
            bet += 10;
        }
        else if (e.getSource() == Chip25){
            bet += 25;
        }
        else if (e.getSource() == Chip50){
            bet += 50;
        }
        else if (e.getSource() == Chip100){
            bet += 100;
        }
        else if (e.getSource() == Chip500){
            bet += 500;
        }
        else if (e.getSource() == Chip1000){
            bet += 1000;
        }
    }

    public void run(Player player, Dealer dealer){
        Scanner scan = new Scanner(System.in); 
        while(player.chips > 1){
            if(deck.size() < 30){
                deck = fullDeck;
                Collections.shuffle(deck); 
            }
            playerMakesBet(scan, player);
            initialDeal(player, dealer);
            initialPlay(scan, player, dealer);
        }
        scan.close();
    }
    
    public void editDisplay(String line){
        display.setText(line);
        repaint();
    }
    
    public void playerMakesBet(Scanner scan, Player player){
        //System.out.println("Enter how much you would like to bet");
        editDisplay("Enter how much you would like to bet");
        readyToBet = true;
        while (bet <= 0 || bet > player.chips){
            if(bet <= 0){
                editDisplay("You must enter a valid amount");
            }
            else if(bet > player.chips){
                editDisplay("You do not have enough chips. Enter another amount");
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {}
        }
        readyToBet = false;
        //int bet = scan.nextInt();
        //while (bet <= 0 || bet > player.chips){
            //System.out.println("You must enter a valid amount");
            //bet = scan.nextInt();
        //}
        player.hands.clear();
        player.newPlayerHand().bet = bet;
        //player.hand.bet = bet;
        player.chips -= bet;
        System.out.println("Chips left: " + player.chips);
        changeButtonVisibility(chipButtons, false);
        labelBet.setText("BET: " + bet);    
        labelChips.setText("Chips: " + player.chips);
    }
    
    public void initialDeal(Player player, Dealer dealer){
        changeButtonVisibility(moveButtons, true);
        dealer.hand.clear();  //maybe put this in a seperate method
        dealer.hand.bust = false;
        player.natural = false;
        dealer.natural = false;
        player.getFirstHand().add(deck.get(0), true); 
        //player.hands.get(0).add(deck.get(0), true); 
        dealer.hand.add(deck.get(1), true); 
        player.getFirstHand().add(deck.get(2), true);  
        dealer.hand.add(deck.get(3), false); 
        deck.removeIf(n -> (deck.indexOf(n) >= 0 && deck.indexOf(n) <= 3)); 
        //System.out.println("You have a " + player.hand.hand.get(0).number + " of " + player.hand.hand.get(0).suit + " and a " + player.hand.hand.get(1).number + " of " + player.hand.hand.get(1).suit);
        player.seeCards(); playerFirstCards = player.getFirstHand().hand; repaint();
        if(player.getFirstHand().checkNatural()){player.natural = true; System.out.println("You have a natural");}
        dealer.seeCards(); dealerCards = dealer.hand.hand; repaint();
        //if(player.hand.hand.get(0).number.equals(player.hand.hand.get(1).number)){player.hand.splittable = true;}
        //else{player.hand.splittable = false;}
        player.checkIfFirstHandCanBeSplit();
    }
    
    public void initialPlay(Scanner scan, Player player, Dealer dealer){
        //first see if dealers faceup card is ace, if so, player can make an insurance bet
        if(dealer.getFaceUpCard().ace){
            System.out.println("Would you like to make an insurance bet?");
            String choice = scan.next();
            while (!"yes".equals(choice) && !"no".equals(choice)){
                System.out.println("Enter 'yes' or 'no'");
                choice = scan.next();
            }
            if(choice.equals("yes")){
                System.out.println("Enter how much you would like to bet");
                System.out.println("You can bet up to half your original bet");
                player.insuranceBet = scan.nextInt();
                while (player.insuranceBet <= 0 || player.insuranceBet > player.chips || player.insuranceBet > player.getFirstHand().bet / 2){
                    System.out.println("You must enter a valid amount");
                    player.insuranceBet = scan.nextInt();
                }
                player.chips -= player.insuranceBet;
                player.insurance = true;
                System.out.println("Chips left: " + player.chips);
                labelChips.setText("Chips: " + player.chips);
            }
        }
        //if face up card is ace of ten card, dealer checks facedown card
        if(dealer.getFaceUpCard().ace || dealer.getFaceUpCard().ten){
            System.out.println("The dealer checks the hole card"); 
            if(dealer.hand.checkNatural()){
                dealer.natural = true;
                dealer.turnOverFaceDownCard();
                System.out.println("The dealer has a natural");
                if(player.insurance){
                    System.out.println("You win twice your insurance bet");
                    player.chips += player.insuranceBet * 3;
                    System.out.println("Chips left: " + player.chips);
                    labelChips.setText("Chips: " + player.chips);
                    player.insurance = false;
                }
            }
            else{
                System.out.println("The dealer does not have a natural");
                if(player.insurance){
                    System.out.println("You lose your insurance bet");
                    player.insurance = false;
                }
            }
        }
        if(player.natural && dealer.natural){ //standoff
            System.out.println("Standoff: Your bet is returned to you");
            player.chips += player.getFirstHand().bet;
            System.out.println("Chips left: " + player.chips);
            labelChips.setText("Chips: " + player.chips);
        }
        else if(player.natural && !dealer.natural){ //player wins
            System.out.println("You receive 1.5 times your bet in winnings");
            player.chips += player.getFirstHand().bet * 2.5;
            System.out.println("Chips left: " + player.chips);
            labelChips.setText("Chips: " + player.chips);
        }
        else{
            play(player, dealer, scan);
        }
    }
    
    public void play(Player player, Dealer dealer, Scanner scan){
        //for every hand
        for(Hand hand : player.hands){
            this.hand = player.hands.indexOf(hand); 
            hand.checkBust();
            while(!hand.bust){
                System.out.println(hand.makeMove());
                String choice = scan.next();
                while (!hand.moveChoices().contains(choice)){ 
                    System.out.println("Enter a valid decision");
                    choice = scan.next();
                } //readyToMove = true;
                if(choice.equals("stand")){break;}
                else if(choice.equals("hit")){ //make hit its own method
                    hit(hand, true);
                    player.seeCards(); repaint();
                }
                else if(choice.equals("double")){
                    player.chips -= hand.bet;
                    hand.bet *= 2;
                    hit(hand, false);
                    player.seeCards(); repaint();
                    hand.doubled = true;
                    hand.checkBust();
                    break;
                }
                else if(choice.equals("split")){ 
                    if(hand.hand.get(0).ace){
                        player.split();
                        hit(player.getFirstHand(), true);
                        hit(player.getSecondHand(), true);
                        player.seeCards(); repaint();
                        player.getFirstHand().checkBust();
                        player.getSecondHand().checkBust();
                        break;
                    }
                    else{
                        player.split();
                        hit(player.getFirstHand(), true);
                        hit(player.getSecondHand(), true);
                        player.seeCards(); repaint();
                    }
                }
            }
        }
        player.turnOverFaceDownCards();
        for(Hand hand : player.hands){
            if(hand.bust){
                System.out.println("You are bust, dealer wins");
            }
            else{
                dealersPlay(player, dealer, hand);
            }
        }
    }
    
    public void hit(Hand hand, Boolean faceUp){
        hand.add(deck.get(0), true); 
        deck.remove(0);
    }
    
    public void dealersPlay(Player player, Dealer dealer, Hand playerHand){
        dealer.turnOverFaceDownCard();
        dealer.seeCards(); repaint();
        while(!dealer.hand.bust){
            if(dealer.hand.getTotalScore() >= 17){
                break;
            }
            else{
                hit(dealer.hand, true);
                System.out.println("Dealer hits"); 
                dealer.seeCards(); repaint();
            }
            dealer.hand.checkBust();
        }
        if(dealer.hand.bust){
            System.out.println("Dealer is bust, you win");
            player.chips += 2 * playerHand.bet;
            System.out.println("Chips left: " + player.chips);
            labelChips.setText("Chips: " + player.chips);
        }
        //settlement
        else if(dealer.hand.getTotalScore() > playerHand.getTotalScore()){
            System.out.println("Dealer wins");
        }
        else if(dealer.hand.getTotalScore() < playerHand.getTotalScore()){
            System.out.println("You win");
            player.chips += 2 * playerHand.bet;
            System.out.println("Chips left: " + player.chips);
            labelChips.setText("Chips: " + player.chips);
        }
        else if(dealer.hand.getTotalScore() == playerHand.getTotalScore()){
            System.out.println("Standoff");
            player.chips += playerHand.bet;
            System.out.println("Chips left: " + player.chips);
            labelChips.setText("Chips: " + player.chips);
        }
    }
    
    public void getDeck() throws FileNotFoundException, IOException{  //shuffles 6 decks of cards to be used
        for(int i = 0; i < 6; i++){
            BufferedReader reader = new BufferedReader(new FileReader("Deck2.txt"));
            for (int j = 0; j < 52; j++){                                                          
                String[] line = reader.readLine().split("[:]");   
                Card one = new Card(line[0], line[1], Integer.valueOf(line[2]), Integer.valueOf(line[3]), Integer.valueOf(line[4]), Integer.valueOf(line[5]), Integer.valueOf(line[6]));                                     
                fullDeck.add(one);
            }
            reader.close();
        }
    }
}

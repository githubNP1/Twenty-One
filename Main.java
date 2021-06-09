package Twenty_One;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Main extends JPanel implements ActionListener{
    ArrayList<Card> deck = new ArrayList<>();
    ArrayList<Card> fullDeck = new ArrayList<>();
    BufferedImage cards;
    BufferedImage table;
    //int[] card;
    ArrayList<Card> dealerCards = new ArrayList<>();
    ArrayList<Card> playerFirstCards = new ArrayList<>();
    ArrayList<Card> playerSecondCards = new ArrayList<>();
    
    JButton hit, stand, Double, split, ok;
    JTextArea display;
    JTextField entry;
    
    JButton Chip1; 
    JButton Chip5; 
    JButton Chip10;
    JButton Chip25; 
    JButton Chip50; 
    JButton Chip100; 
    JButton Chip500; 
    JButton Chip1000; 
    
    Boolean readyToBet = false;
    int bet;
    
    GridBagLayout thelayout;
    GridBagConstraints gbc;
    
    GridLayout layout;
    
    Boolean readyToMove = false;
    int hand;
    
    Player player;
    Dealer dealer;
    
    
    
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
        
        /*JFrame frame = new JFrame();
        frame.add(game);
        frame.setSize(1020, 750); 
        game.setupScreen(game); */
        
        //frame.setVisible(true);
        //game.changeDeck();
        
        /*game.getDeck();
        game.getCardImages();
        game.run(player, dealer);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
        
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
        //thelayout = new GridBagLayout();
        //gbc = new GridBagConstraints();
        //this.setLayout(thelayout);
        
        //layout = new GridLayout(6, 5, 50, 50);
        this.setLayout(null); 
        
        ImageIcon chip1 = new ImageIcon("1 chip.jpg"); 
        ImageIcon chip5 = new ImageIcon("5 chip.jpg"); 
        ImageIcon chip10 = new ImageIcon("10 chip.jpg"); 
        ImageIcon chip25 = new ImageIcon("25 chip.jpg"); 
        ImageIcon chip50 = new ImageIcon("50 chip.jpg"); 
        ImageIcon chip100 = new ImageIcon("100 chip.jpg");  
        ImageIcon chip500 = new ImageIcon("500 chip.jpg"); 
        ImageIcon chip1000 = new ImageIcon("1000 chip.jpg"); 
        
        hit = new JButton("hit");
        stand = new JButton("stand");
        Double = new JButton("double");
        split = new JButton("split");
        ok = new JButton("ok");
        Chip1 = new JButton(chip1);
        Chip5 = new JButton(chip5);
        Chip10 = new JButton(chip10);
        Chip25 = new JButton(chip25);
        Chip50 = new JButton(chip50);
        Chip100 = new JButton(chip100);
        Chip500 = new JButton(chip500);
        Chip1000 = new JButton(chip1000);
        
        //gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        //gbc.fill = GridBagConstraints.BOTH;
        
        /*addobjects(hit, this, thelayout, gbc, 1, 4, 1, 1, 0, 0, 0, 0);
        addobjects(stand, this, thelayout, gbc, 2, 4, 1, 1, 0, 0, 0, 0);
        addobjects(Double, this, thelayout, gbc, 3, 4, 1, 1, 0, 0, 0, 0);
        addobjects(split, this, thelayout, gbc, 4, 4, 1, 1, 0, 0, 0, 0);
        
        addobjects(Chip1, this, thelayout, gbc, 0, 4, 1, 1, 0, 0, 0, 0);
        addobjects(Chip5, this, thelayout, gbc, 0, 5, 1, 1, 0, 0, 0, 0);
        addobjects(Chip10, this, thelayout, gbc, 1, 5, 1, 1, 0, 0, 0, 0);
        addobjects(Chip25, this, thelayout, gbc, 2, 5, 1, 1, 0, 0, 0, 0);
        addobjects(Chip50, this, thelayout, gbc, 3, 5, 1, 1, 0, 0, 0, 0);
        addobjects(Chip100, this, thelayout, gbc, 4, 5, 1, 1, 0, 0, 0, 0);
        addobjects(Chip500, this, thelayout, gbc, 5, 5, 1, 1, 0, 0, 0, 0);
        addobjects(Chip1000, this, thelayout, gbc, 5, 4, 1, 1, 0, 0, 0, 0);*/
                
        //hit.setLocation(100, 100); 
        //stand.setLocation(WIDTH, WIDTH);
        this.add(hit); hit.addActionListener(this);
        this.add(stand); stand.addActionListener(this);
        this.add(Double); Double.addActionListener(this);
        this.add(split); split.addActionListener(this); 
        this.add(ok); ok.addActionListener(this); 
        this.add(Chip1); Chip1.addActionListener(this); Chip1.setBounds(30, 550, 120, 120);
        this.add(Chip5); Chip5.addActionListener(this); Chip5.setBounds(150, 550, 120, 120);
        this.add(Chip10); Chip10.addActionListener(this); Chip10.setBounds(270, 550, 120, 120);
        this.add(Chip25); Chip25.addActionListener(this); Chip25.setBounds(390, 550, 120, 120);
        this.add(Chip50); Chip50.addActionListener(this); Chip50.setBounds(510, 550, 120, 120);
        this.add(Chip100); Chip100.addActionListener(this); Chip100.setBounds(630, 550, 120, 120);
        this.add(Chip500); Chip500.addActionListener(this); Chip500.setBounds(750, 550, 120, 120);
        this.add(Chip1000); Chip1000.addActionListener(this); Chip1000.setBounds(870, 550, 120, 120);
        
        display = new JTextArea(5,10); display.setLineWrap(true); display.setWrapStyleWord(true);
        display.setBounds(700, 200, 100, 50); 
        entry = new JTextField(10);
        entry.setBounds(700, 300, 50, 20);
        this.add(display); 
        this.add(entry); 
        //addobjects(display, this, thelayout, gbc, 5, 0, 1, 1, 20, 0, 50, 0);
        //addobjects(entry, this, thelayout, gbc, 5, 2, 1, 1, 20, 0, 50, 0);
        
        //Chip1.setLocation(500, 20);
        
        repaint();
    }
    
    public void addobjects(Component componente, Container yourcontainer, GridBagLayout layout, GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight, int insetsTop, int insetsLeft,int insetsBottom,int insetsRight){

        gbc.gridx = gridx;
        gbc.gridy = gridy;

        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        
        gbc.insets = new Insets(insetsTop, insetsLeft, insetsBottom, insetsRight);

        layout.setConstraints(componente, gbc);
        yourcontainer.add(componente);
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
        else if(e.getSource() == ok){
            if(readyToBet){
                bet = Integer.valueOf(entry.getText()); 
                readyToBet = false;
            }
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
    }
    
    public void initialDeal(Player player, Dealer dealer){
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
        }
        else if(player.natural && !dealer.natural){ //player wins
            System.out.println("You receive 1.5 times your bet in winnings");
            player.chips += player.getFirstHand().bet * 2.5;
            System.out.println("Chips left: " + player.chips);
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
        }
        //settlement
        else if(dealer.hand.getTotalScore() > playerHand.getTotalScore()){
            System.out.println("Dealer wins");
        }
        else if(dealer.hand.getTotalScore() < playerHand.getTotalScore()){
            System.out.println("You win");
            player.chips += 2 * playerHand.bet;
            System.out.println("Chips left: " + player.chips);
        }
        else if(dealer.hand.getTotalScore() == playerHand.getTotalScore()){
            System.out.println("Standoff");
            player.chips += playerHand.bet;
            System.out.println("Chips left: " + player.chips);
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
    //g.drawImage(image, 74, 99, 146, 195, 74, 99, 146, 195, null);
    public void changeDeck() throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader("Deck.txt"));
        String[] line = reader.readLine().split("[:]");   
        int x1d = Integer.valueOf(line[3]) - 74;
        int y1d = Integer.valueOf(line[4]) - 99;
        int x2d = Integer.valueOf(line[5]) - 146;
        int y2d = Integer.valueOf(line[6]) - 195;
        
        PrintStream one = new PrintStream(new File("Deck2.txt"));
        one.append(line[0] + ":" +  line[1] + ":" + line[2] + ":" + (Integer.valueOf(line[3]) - x1d) + ":" + (Integer.valueOf(line[4]) - y1d) + ":" + (Integer.valueOf(line[5]) - x2d) + ":" + (Integer.valueOf(line[6]) - y2d) + "\r\n");
        for(int i = 0; i < 51; i++){
            line = reader.readLine().split("[:]"); 
            one.append(line[0] + ":" +  line[1] + ":" + line[2] + ":" + (Integer.valueOf(line[3]) - x1d) + ":" + (Integer.valueOf(line[4]) - y1d) + ":" + (Integer.valueOf(line[5]) - x2d) + ":" + (Integer.valueOf(line[6]) - y2d) + "\r\n");
        }
        
        reader.close();
    }

}

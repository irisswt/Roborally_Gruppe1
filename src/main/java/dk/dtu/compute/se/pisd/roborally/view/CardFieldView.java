/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.CommandCard;
import dk.dtu.compute.se.pisd.roborally.model.CommandCardField;
import dk.dtu.compute.se.pisd.roborally.model.Phase;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

/**
 * Class responsible for the GUI of the cards and the fields they can be placed in.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class CardFieldView extends GridPane implements ViewObserver {

    // This data format helps avoiding transfers of e.g. Strings from other
    // programs which can copy/paste Strings.
    final public static  DataFormat ROBO_RALLY_CARD = new DataFormat("games/roborally/cards");

    final public static int CARDFIELD_WIDTH = 65;
    final public static int CARDFIELD_HEIGHT = 100;

    final public static Border BORDER = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2)));

    final public static Background BG_DEFAULT = new Background(new BackgroundFill(Color.WHITE, null, null));
    final public static Background BG_DRAG = new Background(new BackgroundFill(Color.GRAY, null, null));
    final public static Background BG_DROP = new Background(new BackgroundFill(Color.LIGHTGRAY, null, null));

    final public static Background BG_ACTIVE = new Background(new BackgroundFill(Color.YELLOW, null, null));
    final public static Background BG_DONE = new Background(new BackgroundFill(Color.GREENYELLOW,  null, null));

    private CommandCardField field;

    private Label label;

    private GameController gameController;

    /**
     * Constructor for CardFieldView. Creates the GUI element for the field where the cards are along with mouse handlers.
     * @param gameController gameController object to handle the generation of cards.
     * @param field Where the card fields needs to be attached to on the board.
     */
    public CardFieldView(@NotNull GameController gameController, @NotNull CommandCardField field) {
        this.gameController = gameController;
        this.field = field;

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(5, 5, 5, 5));

        this.setBorder(BORDER);
        this.setBackground(BG_DEFAULT);

        this.setPrefWidth(CARDFIELD_WIDTH);
        this.setMinWidth(CARDFIELD_WIDTH);
        this.setMaxWidth(CARDFIELD_WIDTH);
        this.setPrefHeight(CARDFIELD_HEIGHT);
        this.setMinHeight(CARDFIELD_HEIGHT);
        this.setMaxHeight(CARDFIELD_HEIGHT);

        label = new Label("This is a slightly longer text");
        label.setWrapText(true);
        label.setMouseTransparent(true);
        this.add(label, 0, 0);

        this.setOnDragDetected(new OnDragDetectedHandler());
        this.setOnDragOver(new OnDragOverHandler());
        this.setOnDragEntered(new OnDragEnteredHandler());
        this.setOnDragExited(new OnDragExitedHandler());
        this.setOnDragDropped(new OnDragDroppedHandler());
        this.setOnDragDone(new OnDragDoneHandler());

        field.attach(this);
        update(field);
    }

    /**
     * A method to give the players an easy overview of the order of the cards.
     * @Author Ekkart Kindler
     * @param cardField the place the cards can be placed.
     * @return Strings representing the places for the cards.
     */
    private String cardFieldRepresentation(CommandCardField cardField) {
        if (cardField.player != null) {

            for (int i = 0; i < Player.NO_REGISTERS; i++) {
                CommandCardField other = cardField.player.getProgramField(i);
                if (other == cardField) {
                    return "P," + i;
                }
            }

            for (int i = 0; i < Player.NO_CARDS; i++) {
                CommandCardField other = cardField.player.getCardField(i);
                if (other == cardField) {
                    return "C," + i;
                }
            }
        }
        return null;

    }

    /**
     * A method to identify the card field from the string representation.
     * @Author Ekkart Kindler
     * @param rep the string representation made in the cardFieldRepresentation method.
     * @return a field corresponding to the string representation.
     */
    private CommandCardField cardFieldFromRepresentation(String rep) {
        if (rep != null && field.player != null) {
            String[] strings = rep.split(",");
            if (strings.length == 2) {
                int i = Integer.parseInt(strings[1]);
                if ("P".equals(strings[0])) {
                    if (i < Player.NO_REGISTERS) {
                        return field.player.getProgramField(i);
                    }
                } else if ("C".equals(strings[0])) {
                    if (i < Player.NO_CARDS) {
                        return field.player.getCardField(i);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Updates the card field in the GUI.
     * @param subject inherited from subject and attached to an observer.
     */
    @Override
    public void updateView(Subject subject) {
        if (subject == field && subject != null) {
            CommandCard card = field.getCard();
            if (card != null && field.isVisible()) {
                label.setText(card.getName());
            } else {
                label.setText("");
            }
        }
    }

    private class OnDragDetectedHandler implements EventHandler<MouseEvent> {
        /**
         * Method to handle the start of a drag action by a mouse input.
         * @Author Ekkart Kindler
         * @param event the mouse drag action.
         */
        @Override
        public void handle(MouseEvent event) {
            Object t = event.getTarget();
            if (t instanceof CardFieldView) {
                CardFieldView source = (CardFieldView) t;
                CommandCardField cardField = source.field;
                if (cardField != null &&
                        cardField.getCard() != null &&
                        cardField.player != null &&
                        cardField.player.board != null &&
                        cardField.player.board.getPhase().equals(Phase.PROGRAMMING)) {
                    Dragboard db = source.startDragAndDrop(TransferMode.MOVE);
                    Image image = source.snapshot(null, null);
                    db.setDragView(image);

                    ClipboardContent content = new ClipboardContent();
                    content.put(ROBO_RALLY_CARD, cardFieldRepresentation(cardField));

                    db.setContent(content);
                    source.setBackground(BG_DRAG);
                }
            }
            event.consume();
        }

    }

    private class OnDragOverHandler implements EventHandler<DragEvent> {
        /**
         * Method to handle a card being dragged.
         * @Author Ekkart Kindler
         * @param event the mouse drag action
         */
        @Override
        public void handle(DragEvent event) {
            Object t = event.getTarget();
            if (t instanceof CardFieldView) {
                CardFieldView target = (CardFieldView) t;
                CommandCardField cardField = target.field;
                if (cardField != null &&
                        (cardField.getCard() == null || event.getGestureSource() == target) &&
                        cardField.player != null &&
                        cardField.player.board != null) {
                    if (event.getDragboard().hasContent(ROBO_RALLY_CARD)) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
            }
            event.consume();
        }

    }

    private class OnDragEnteredHandler implements EventHandler<DragEvent> {
        /**
         * Method to handle when a card is being dragged over a card field.
         * @Author Ekkart Kindler
         * @param event the mouse drag action
         */
        @Override
        public void handle(DragEvent event) {
            Object t = event.getTarget();
            if (t instanceof CardFieldView) {
                CardFieldView target = (CardFieldView) t;
                CommandCardField cardField = target.field;
                if (cardField != null &&
                        cardField.getCard() == null &&
                        cardField.player != null &&
                        cardField.player.board != null) {
                    if (event.getGestureSource() != target &&
                            event.getDragboard().hasContent(ROBO_RALLY_CARD)) {
                        target.setBackground(BG_DROP);
                    }
                }
            }
            event.consume();
        }

    }

    private class OnDragExitedHandler implements EventHandler<DragEvent> {
        /**
         * Method to handle when the dragging of a card stops.
         * @Author Ekkart Kindler
         * @param event the mouse drag action.
         */
        @Override
        public void handle(DragEvent event) {
            Object t = event.getTarget();
            if (t instanceof CardFieldView) {
                CardFieldView target = (CardFieldView) t;
                CommandCardField cardField = target.field;
                if (cardField != null &&
                        cardField.getCard() == null &&
                        cardField.player != null &&
                        cardField.player.board != null) {
                    if (event.getGestureSource() != target &&
                            event.getDragboard().hasContent(ROBO_RALLY_CARD)) {
                        target.setBackground(BG_DEFAULT);
                    }
                }
            }
            event.consume();
        }

    }

    private class OnDragDroppedHandler implements EventHandler<DragEvent> {
        /**
         * Method to handle when you let go of the mouse and stop dragging.
         * @Author Ekkart Kindler
         * @param event the mouse drag action.
         */
        @Override
        public void handle(DragEvent event) {
            Object t = event.getTarget();
            if (t instanceof CardFieldView) {
                CardFieldView target = (CardFieldView) t;
                CommandCardField cardField = target.field;

                Dragboard db = event.getDragboard();
                boolean success = false;
                if (cardField != null &&
                        cardField.getCard() == null &&
                        cardField.player != null &&
                        cardField.player.board != null) {
                    if (event.getGestureSource() != target &&
                            db.hasContent(ROBO_RALLY_CARD)) {
                        Object object = db.getContent(ROBO_RALLY_CARD);
                        if (object instanceof String) {
                            CommandCardField source = cardFieldFromRepresentation((String) object);
                            if (source != null && gameController.moveCards(source, cardField)) {
                                // CommandCard card = source.getCard();
                                // if (card != null) {
                                // if (gameController.moveCards(source, cardField)) {
                                    // cardField.setCard(card);
                                    success = true;
                                // }
                            }
                        }
                    }
                }
                event.setDropCompleted(success);
                target.setBackground(BG_DEFAULT);
            }
            event.consume();
        }

    }

    private class OnDragDoneHandler implements EventHandler<DragEvent> {
        /**
         * Method to handle that you're done dragging a card.
         * @Author Ekkart Kindler
         * @param event the mouse drag action.
         */
        @Override
        public void handle(DragEvent event) {
            Object t = event.getTarget();
            if (t instanceof CardFieldView) {
                CardFieldView source = (CardFieldView) t;
                source.setBackground(BG_DEFAULT);
            }
            event.consume();
        }

    }

}





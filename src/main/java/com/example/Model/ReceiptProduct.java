package com.example.Model;

import java.sql.Timestamp;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ReceiptProduct {
    private int receiptId;
    private int itemId;
    private String productName;
    private int quantity;
    private Timestamp receiptDate;
    private String notes;
    private boolean isprocessed;

    private BooleanProperty highlighted = new SimpleBooleanProperty(false);

    public ReceiptProduct(int receiptId, int itemId, String productName, int quantity, Timestamp receiptDate,
            String notes, boolean isprocessed) {
        this.receiptId = receiptId;
        this.itemId = itemId;
        this.productName = productName;
        this.quantity = quantity;
        this.receiptDate = receiptDate;
        this.notes = notes;
        this.isprocessed = isprocessed;
    }

    public void setIsprocessed(boolean isprocessed) {
        this.isprocessed = isprocessed;
    }

    public boolean getIsprocessed() {
        return isprocessed;
    }

    public boolean isHighlighted() {
        return highlighted.get();
    }

    public void setHighlighted(boolean value) {
        highlighted.set(value);
    }

    public BooleanProperty highlightedProperty() {
        return highlighted;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public Timestamp getReceiptDate() {
        return receiptDate;
    }

    public String getNotes() {
        return notes;
    }
}

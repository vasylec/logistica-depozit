package com.example.Model;

import java.sql.Timestamp;

public class Receipt {
    private int id;
    private int supplierId;
    private int warehouseId;
    private Timestamp receiptDate;
    private String documentNumber;
    private String notes;

    public Receipt(int id, int supplierId, int warehouseId, Timestamp receiptDate, String documentNumber, String notes) {
        this.id = id;
        this.supplierId = supplierId;
        this.warehouseId = warehouseId;
        this.receiptDate = receiptDate;
        this.documentNumber = documentNumber;
        this.notes = notes;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }
    public int getWarehouseId() { return warehouseId; }
    public void setWarehouseId(int warehouseId) { this.warehouseId = warehouseId; }
    public Timestamp getReceiptDate() { return receiptDate; }
    public void setReceiptDate(Timestamp receiptDate) { this.receiptDate = receiptDate; }
    public String getDocumentNumber() { return documentNumber; }
    public void setDocumentNumber(String documentNumber) { this.documentNumber = documentNumber; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}

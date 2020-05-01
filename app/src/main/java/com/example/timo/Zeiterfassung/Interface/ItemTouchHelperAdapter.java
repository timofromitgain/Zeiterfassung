package com.example.timo.Zeiterfassung.Interface;

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onStopDrag(int fromPosition, int toPosition);
    void onItemDismiss(int position);

}

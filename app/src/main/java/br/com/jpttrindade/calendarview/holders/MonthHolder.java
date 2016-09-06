package br.com.jpttrindade.calendarview.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by joaotrindade on 06/09/16.
 */
public class MonthHolder extends RecyclerView.ViewHolder {

    public TextView tv_month;

    public MonthHolder(View itemView) {
        super(itemView);
        tv_month = (TextView) itemView;
    }


}

package br.com.jpttrindade.calendarview.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import br.com.jpttrindade.calendarview.holders.MonthHolder;

/**
 * Created by joaotrindade on 06/09/16.
 */
public class CalendarAdapter extends RecyclerView.Adapter {


    private final List<String> mMonths;
    private final Context mContext;
    private final int mYear;

    public CalendarAdapter(Context context, String[] months, int year) {
        mContext = context;
        mMonths = Arrays.asList(months);
        mYear = year;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TextView tv = new TextView(mContext);

        MonthHolder mh = new MonthHolder(tv);

        return mh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if( holder instanceof MonthHolder) {
            MonthHolder mh = (MonthHolder) holder;
            mh.tv_month.setText(mMonths.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mMonths.size();
    }
}

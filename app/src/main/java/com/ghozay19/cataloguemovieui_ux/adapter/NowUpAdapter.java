package com.ghozay19.cataloguemovieui_ux.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ghozay19.cataloguemovieui_ux.DetailActivity;
import com.ghozay19.cataloguemovieui_ux.Movieitem;
import com.ghozay19.cataloguemovieui_ux.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NowUpAdapter extends RecyclerView.Adapter<NowUpAdapter.ViewHolder> {

    private ArrayList<Movieitem> mMovieItem = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public NowUpAdapter(final Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<Movieitem> items) {
        mMovieItem = items;
        notifyDataSetChanged();
    }

    public void addItem(final Movieitem item) {
        mMovieItem.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    public Movieitem getItem(int position) {
        return mMovieItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (mMovieItem == null) return 0;
        return mMovieItem.size();
    }

    public void clearData() {
        mMovieItem.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_title_movie.setText(mMovieItem.get(position).getMov_title());
        holder.tv_synopsis_movie.setText(mMovieItem.get(position).getMov_synopsis());
        holder.tv_title_movie.setText(mMovieItem.get(position).getMov_title());

        Picasso.with(context).load("http://image.tmdb.org/t/p/w154/" + mMovieItem.get(position)
                .getMov_poster()).placeholder(context.getResources()
                .getDrawable(R.drawable.ic_launcher_background))
                .error(context.getResources().getDrawable(R.drawable.image)).into(holder.iv_poster_movie);

        String retrieveDate = mMovieItem.get(position).getMov_releasedate();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = date_format.parse(retrieveDate);
            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            String release_date = new_date_format.format(date);
            holder.tv_rlsdate_movie.setText(release_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, (context.getString(R.string.share)) + " " + mMovieItem
                        .get(position)
                        .getMov_title(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, (context.getString(R.string.favorite)) + " " + mMovieItem
                        .get(position).getMov_title(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);


                intent.putExtra(DetailActivity.EXTRA_TITLE, mMovieItem.get(position).getMov_title());
                intent.putExtra(DetailActivity.EXTRA_OVERVIEW, mMovieItem.get(position).getMov_synopsis());
                intent.putExtra(DetailActivity.EXTRA_POSTER_JPG, mMovieItem.get(position).getMov_poster());
                intent.putExtra(DetailActivity.EXTRA_RATE, mMovieItem.get(position).getMov_rate());
                intent.putExtra(DetailActivity.EXTRA_RATE_COUNT, mMovieItem.get(position).getMov_rate_count());
                intent.putExtra(DetailActivity.EXTRA_RELEASE_DATE, mMovieItem.get(position).getMov_releasedate());


                context.startActivity(intent);

            }
        });

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_poster_movie;
        TextView tv_title_movie;
        TextView tv_synopsis_movie;
        TextView tv_rlsdate_movie;
        Button btn_favorite;
        Button btn_share;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            iv_poster_movie = (ImageView) itemView.findViewById(R.id.iv_poster_cv);
            tv_title_movie = (TextView) itemView.findViewById(R.id.title_detail_cv);
            tv_synopsis_movie = (TextView) itemView.findViewById(R.id.overview_detail_cv);
            tv_rlsdate_movie = (TextView) itemView.findViewById(R.id.release_date_detail_cv);
            btn_favorite = (Button) itemView.findViewById(R.id.btn_favorite);
            btn_share = (Button) itemView.findViewById(R.id.btn_share);
        }

    }
}
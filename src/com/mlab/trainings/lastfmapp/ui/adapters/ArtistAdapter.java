package com.mlab.trainings.lastfmapp.ui.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.mlab.trainings.lastfmapp.R;
import com.mlab.trainings.lastfmapp.model.Artist;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ArtistAdapter extends BaseAdapter
{

	private OnNewItemsLoadCallback callback;
    private LayoutInflater inflater;

    private List<Artist> artists;
	private Context context;
	
	public void setOnNew(OnNewItemsLoadCallback callback)
	{
		this.callback = callback;
	}

	public ArtistAdapter(Context context)
	{
		this.context = context;
	}
	
	public void addArtists(List<Artist> newArtists)
	{
        if(artists == null) {
            artists = new ArrayList<Artist>();
        }

        this.artists.addAll(newArtists);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount()
	{

        return artists==null? 0:artists.size() + 1;
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}
	
	@Override
	public int getViewTypeCount()
	{
		return 2;
	}


    public void refresh() {

        if (artists != null) {
            artists.clear();
        } else {
            artists = new ArrayList<Artist>();
        }
        notifyDataSetChanged();
    }
	@Override
	public int getItemViewType(int position)
	{
		return position == artists.size() ? 1 : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
        ViewHolder holder;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(position == artists.size()) {
            convertView = inflater.inflate(R.layout.artist_list_item, parent, false);

            if (callback != null) {
                callback.onNewItems(position);
            }
            return convertView;
        }


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.artist_list_item, parent, false);

            holder = new ViewHolder();
            holder.artistName = (TextView) convertView.findViewById(R.id.artist_name);
            holder.artistListeners = (TextView) convertView.findViewById(R.id.artist_listener_count);
            holder.artistImage = (ImageView) convertView.findViewById(R.id.artist_image);


            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();
        Artist artist = artists.get(position);
        holder.artistName.setText(artist.getName());
        holder.artistListeners.setText(Integer.toString(artist.getListeners()));

        Artist current = artists.get(position);
        ImageLoader.getInstance().displayImage(current.getLogoUrl(), holder.artistImage);

        /*final View finalConvertView = convertView;
        final ViewHolder fullHolder = holder;

        current.setOnFullInfoCallback(new Artist.onFullLoadedCallback() {
            @Override
            public void onFullInfoLoader(Artist artist) {
                if (finalConvertView != null) {
                    fullHolder.artistYear = (TextView) finalConvertView.findViewById(R.id.artist_year);
                    fullHolder.artistPlayerCount = (TextView) finalConvertView.findViewById(R.id.artist_play_count);

                    fullHolder.artistAbout = (TextView) finalConvertView.findViewById(R.id.artist_about);
                    finalConvertView.setTag(fullHolder);

                }
                fullHolder.artistYear.setText(Integer.toString(artist.getStartYear()));
                fullHolder.artistPlayerCount.setText(Integer.toString(artist.getPlayerCount()));
                fullHolder.artistAbout.setText(artist.getSummary());

            }
        });
        holder = fullHolder;*/

        return convertView;

	}
	
	public static interface OnNewItemsLoadCallback
	{
		public void onNewItems(int offset);
	}



    private static  final class ViewHolder {
        TextView artistName;
        TextView artistListeners;
        ImageView artistImage;
        TextView artistYear;
        TextView artistPlayerCount;
        TextView artistAbout;


    }

}

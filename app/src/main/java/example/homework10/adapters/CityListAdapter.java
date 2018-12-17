package example.homework10.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.homework10.R;
import example.homework10.apis.open_weather_map.pojos.CitiesParcelable;
import example.homework10.handlers.StringFormatHandler;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<CitiesParcelable> cities;
    private final ItemClickCallback callback;
    private final String TAG = "Logs";

    public CityListAdapter(Context context, ItemClickCallback callback, List<CitiesParcelable> cities) {
        this.cities = cities;
        this.callback = callback;
        this.inflater = LayoutInflater.from(context);
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CitiesParcelable city = cities.get(position);
        holder.itemCityName.setText(city.getName());
        holder.itemCountryName.setText(city.getCountry());
        holder.itemTemperature.setText(city.getTemperature() + " °С");
        holder.itemDayTime.setText(StringFormatHandler.fromUNIXtoReadable(city.getDatetime()));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    @Override
    public long getItemId(int position) {
        return cities.get(position).hashCode();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView itemCityName, itemCountryName, itemTemperature, itemDayTime;

        ViewHolder(View itemView) {
            super(itemView);
            itemCityName = itemView.findViewById(R.id.tv_city_name);
            itemCountryName = itemView.findViewById(R.id.tv_country_name);
            itemTemperature = itemView.findViewById(R.id.tv_temperature);
            itemDayTime = itemView.findViewById(R.id.tv_time);
            itemView.setOnClickListener(view -> callback.onItemClick(getAdapterPosition()));
        }
    }
}

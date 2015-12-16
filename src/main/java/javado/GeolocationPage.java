package javado;

import lombok.val;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.html5.geolocation.AjaxGeolocationBehavior;

public class GeolocationPage extends HomePage {

  public GeolocationPage(final PageParameters parameters) {
    super(parameters);

    val geolocator = new WebMarkupContainer("geolocator");
    add(geolocator);

    geolocator.add(new AjaxGeolocationBehavior() {

      @Override
      protected void onGeoAvailable(AjaxRequestTarget target, String latitude, String longitude) {
        System.out.println(System.currentTimeMillis() + "We received a location. Latitude: " + latitude + " Longitude: " + longitude);
      }

      @Override
      protected void onNotAvailable(AjaxRequestTarget target, String errorCode, String errorMessage) {
        System.out.println(System.currentTimeMillis() + "An error occured. Error code: " + errorCode + " Error message:" + errorMessage);
      }

      @Override
      public int getTimeout() {
        // milliseconds
        return 5000;
      }

    });
    // 複数回送信
    // geolocator.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5L)));

  }

}

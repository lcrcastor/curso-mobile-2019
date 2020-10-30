package ar.com.santander.rio.mbanking.app.ui.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.List;

public class CustomSeekBar {
    private int a;
    private int b;
    private int c;
    private int[] d = null;
    private List<Integer> e = null;
    private int f;
    private int g = -7829368;
    private Context h;
    private LinearLayout i;
    private SeekBar j;
    private boolean k = true;
    /* access modifiers changed from: private */
    public int l = -1;
    /* access modifiers changed from: private */
    public int m = -1;
    /* access modifiers changed from: private */
    public boolean n = true;

    interface Event {
        void a();
    }

    public interface SeekBarEvents {
        void onProgressChanged(SeekBar seekBar, int i, boolean z);

        void onStartTrackingTouch(SeekBar seekBar);

        void onStopTrackingTouch(SeekBar seekBar);
    }

    public CustomSeekBar(Context context) {
        this.h = context;
    }

    public CustomSeekBar(Context context, List<Integer> list, int i2) {
        this.h = context;
        this.e = list;
        this.g = i2;
    }

    public CustomSeekBar(Context context, int i2, int i3) {
        this.h = context;
        this.f = i2;
        this.g = i3;
    }

    public CustomSeekBar(Context context, int i2, int i3, int i4, int i5) {
        this.c = i3;
        this.b = i4;
        this.a = i5;
        int floor = (int) (Math.floor((double) ((i4 - i3) / i5)) - 1.0d);
        this.d = new int[floor];
        int i6 = 0;
        while (i6 < floor) {
            int[] iArr = this.d;
            int i7 = i6 == 0 ? i3 : i6 == floor + -1 ? i4 : ((i6 + 1) * i5) + i3;
            iArr[i6] = i7;
            i6++;
        }
        this.h = context;
        this.f = i4;
        this.g = i2;
    }

    public SeekBar getSeekBar() {
        return this.j;
    }

    public CustomSeekBar addSeekBar(LinearLayout linearLayout) {
        buildSeekBar(linearLayout, new Event() {
            public void a() {
            }
        });
        return this;
    }

    public void buildSeekBar(LinearLayout linearLayout, Event event) {
        if (linearLayout instanceof LinearLayout) {
            linearLayout.removeAllViews();
            linearLayout.setOrientation(1);
            this.j = (SeekBar) ((LayoutInflater) this.h.getSystemService("layout_inflater")).inflate(R.layout.custom_seekbar_recalificacion, null).findViewById(R.id.sbCustomRecalificacion);
            this.i = new LinearLayout(this.h);
            this.i.setOrientation(0);
            this.i.setLayoutParams(new LayoutParams(-1, -2));
            event.a();
            linearLayout.addView(this.j);
            linearLayout.addView(this.i);
            return;
        }
        Log.e("CustomSeekBar", " Parent is not a LinearLayout");
    }

    public CustomSeekBar setMax() {
        if (this.e != null) {
            b();
        } else if (this.d != null) {
            c();
        } else {
            a();
        }
        return this;
    }

    private void a() {
        this.j.setMax(this.f - 1);
        int i2 = 0;
        while (i2 < this.f) {
            int i3 = i2 + 1;
            a(i2, String.valueOf(i3));
            i2 = i3;
        }
    }

    private void b() {
        this.j.setMax(this.e.size() - 1);
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            a(i2, String.valueOf(this.e.get(i2)));
        }
    }

    private void c() {
        this.j.setMax(this.d.length - 1);
        for (int i2 = 0; i2 < this.d.length; i2++) {
            a(i2, String.valueOf(this.d[i2]));
        }
    }

    public CustomSeekBar setRange(List<Integer> list) {
        this.e = list;
        return this;
    }

    public CustomSeekBar setMinMaxRange(int i2, int i3, int i4) {
        this.c = i2;
        this.b = i3;
        this.a = i4;
        ArrayList arrayList = new ArrayList();
        while (i2 < i3) {
            arrayList.add(Integer.valueOf(i2));
            i2 += i4;
        }
        arrayList.add(Integer.valueOf(i3));
        this.d = new int[arrayList.size()];
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            this.d[i5] = ((Integer) arrayList.get(i5)).intValue();
        }
        return this;
    }

    private void a(int i2, String str) {
        TextView textView = new TextView(this.h);
        textView.setText(str);
        textView.setTextColor(this.g);
        textView.setGravity(17);
        this.i.addView(textView);
        textView.setLayoutParams(a(i2 == this.f + -1 ? BitmapDescriptorFactory.HUE_RED : 1.0f));
        textView.setVisibility(this.k ? 0 : 8);
        textView.setRotation(45.0f);
    }

    /* access modifiers changed from: 0000 */
    public LayoutParams a(float f2) {
        return new LayoutParams(-2, -2, f2);
    }

    public CustomSeekBar setEvents(final SeekBarEvents seekBarEvents) {
        this.j.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (CustomSeekBar.this.l != -1) {
                    CustomSeekBar.this.d(CustomSeekBar.this.l);
                }
                if (CustomSeekBar.this.m != -1) {
                    CustomSeekBar.this.m = -1;
                }
                if (CustomSeekBar.this.n) {
                    seekBarEvents.onProgressChanged(seekBar, i, z);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBarEvents.onStartTrackingTouch(seekBar);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarEvents.onStopTrackingTouch(seekBar);
            }
        });
        return this;
    }

    public CustomSeekBar showLabels(boolean z) {
        this.k = z;
        return this;
    }

    public int getCurrentValue() {
        setCurrentProgress(this.j.getProgress());
        if (this.e != null) {
            return ((Integer) this.e.get(this.j.getProgress())).intValue();
        }
        if (this.d != null) {
            return this.d[this.j.getProgress()];
        }
        return this.j.getProgress();
    }

    public void setCurrentValue(int i2) {
        this.j.setProgress(a(i2));
        setCurrentProgress(this.j.getProgress());
    }

    private int a(int i2) {
        if (this.e != null) {
            return b(i2);
        }
        if (this.d != null) {
            return c(i2);
        }
        return this.j.getProgress();
    }

    private int b(int i2) {
        for (int i3 = 0; i3 < this.e.size(); i3++) {
            if (((Integer) this.e.get(i3)).intValue() == i2) {
                return i3;
            }
        }
        return 0;
    }

    private int c(int i2) {
        for (int i3 = 0; i3 < this.d.length; i3++) {
            if (this.d[i3] == i2) {
                return i3;
            }
        }
        return 0;
    }

    public void setCurrentProgress(int i2) {
        this.j.setProgress(i2);
    }

    public int getCurrentProgress() {
        return this.j.getProgress();
    }

    public CustomSeekBar setMaxValueAllowed(int i2) {
        int i3;
        this.l = i2;
        int i4 = 0;
        int i5 = -1;
        if (this.e != null) {
            while (true) {
                if (i4 >= this.e.size()) {
                    i3 = -1;
                    break;
                } else if (((Integer) this.e.get(i4)).intValue() > i2) {
                    i3 = i4 - 1;
                    break;
                } else if (((Integer) this.e.get(i4)).intValue() == i2) {
                    i3 = i4;
                    break;
                } else {
                    i4++;
                }
            }
            i5 = i3 == -1 ? this.e.size() : i3;
        } else if (this.d != null) {
            while (true) {
                if (i4 >= this.d.length) {
                    i4 = -1;
                    break;
                } else if (this.d[i4] > i2) {
                    i4--;
                    break;
                } else if (this.d[i4] == i2) {
                    break;
                } else {
                    i4++;
                }
            }
            i5 = i4 == -1 ? this.d.length : i4;
        }
        this.j.setSecondaryProgress(i5);
        return this;
    }

    /* access modifiers changed from: private */
    public void d(int i2) {
        if (getCurrentValue() > i2) {
            this.n = false;
            this.m = getCurrentProgress() - 1;
            this.j.setProgress(this.m);
        } else if (this.m == -1) {
            this.n = true;
            this.j.setProgress(getCurrentProgress());
        }
    }
}

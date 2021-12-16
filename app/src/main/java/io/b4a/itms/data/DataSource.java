package io.b4a.itms.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.b4a.itms.R;
import io.b4a.itms.data.model.BusStationInfo;
import io.b4a.itms.data.model.Park;

final public class DataSource {

    /**
     * 公交站信息
     */
    public final static List<BusStationInfo> busStationInfoList = Arrays.asList(
            new BusStationInfo(1, 13213),
            new BusStationInfo(2, 24540),
            new BusStationInfo(3, 12344)
    );


    /**
     * 停车场图片
     */
    private final static int[] parkImageList = {
            R.drawable.image_park_01, R.drawable.image_park_02, R.drawable.image_park_03,
            R.drawable.image_park_04, R.drawable.image_park_05, R.drawable.image_park_06,
            R.drawable.image_park_07, R.drawable.image_park_08
    };

    @Deprecated
    public final static List<Park> getParkList() {
        List list = new ArrayList<Park>();
        for (int i = 1; i <= parkImageList.length; i++) {
            list.add(new Park(String.valueOf(i), "新登停车场" + i, "39.5040579764", "116.4990421601",
                    i + "20", i + "00", i + "4", i + "0 元/小时", "麓西南路 " + i + " 号",
                    "对外开放", i + "0 元/小时，最高 " + (i * 10 * 4) + "0 元/天", parkImageList[i - 1]
            ));
        }
        return list;
    }

    /**
     * 停车场信息
     */
    public final static List<Park> parkList = Arrays.asList(
            new Park("暨大基因停车场", "39.5040579764", "116.4990421601",
                    "220", "30", "14", "10 元/小时", "开源大道206号",
                    "对外开放", "10 元/小时，最高 40 元/天", parkImageList[0]
            ),
            new Park("府前大厦停车场", "39.5040579764", "116.4990421601",
                    "220", "30", "14", "10 元/小时", "青城路24号",
                    "对外开放", "10 元/小时，最高 40 元/天", parkImageList[1]
            ),
            new Park("阳光停车场", "39.5040579764", "116.4990421601",
                    "220", "30", "14", "10 元/小时", "幸福路6号",
                    "对外开放", "10 元/小时，最高 40 元/天", parkImageList[2]
            ),
            new Park("悦来酒店停车场", "39.5040579764", "116.4990421601",
                    "220", "30", "14", "10 元/小时", "悦来路14号",
                    "对外开放", "10 元/小时，最高 40 元/天", parkImageList[3]
            ),
            new Park("外特宾馆停车场", "39.5040579764", "116.4990421601",
                    "220", "30", "14", "10 元/小时", "外特大道206号",
                    "对外开放", "10 元/小时，最高 40 元/天", parkImageList[4]
            ),
            new Park("虚拟停车场", "39.5040579764", "116.4990421601",
                    "220", "30", "14", "10 元/小时", "虚拟路123号",
                    "对外开放", "10 元/小时，最高 40 元/天", parkImageList[5]
            ),
            new Park("抚顺停车场", "39.5040579764", "116.4990421601",
                    "220", "30", "14", "10 元/小时", "抚顺路11号",
                    "对外开放", "10 元/小时，最高 40 元/天", parkImageList[6]
            ),
            new Park("绿荫停车场", "39.5040579764", "116.4990421601",
                    "220", "30", "14", "10 元/小时", "绿荫路59号",
                    "对外开放", "10 元/小时，最高 40 元/天", parkImageList[7]
            )
    );

}

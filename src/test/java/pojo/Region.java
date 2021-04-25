package pojo;

public class Region {

    private int region_id;
    private String Region_name;


    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public void setRegion_name(String region_name) {
        Region_name = region_name;
    }

    public String getRegion_name() {
        return Region_name;
    }

    @Override
    public String toString() {
        return "Region{" +
                "region_id=" + region_id +
                ", Region_name='" + Region_name + '\'' +
                '}';
    }
}

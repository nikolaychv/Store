package com.citb408;

public enum GoodsCategory {
     FOODSTUFF, NONFOOD_STUFF;
     private double percentage;

     GoodsCategory() {
     }

     GoodsCategory(double percentage) {
          this.percentage = percentage;
     }

     public double getPercentage() {
          return percentage;
     }

     public void setPercentage(double percentage) {
          this.percentage = percentage;
     }
}

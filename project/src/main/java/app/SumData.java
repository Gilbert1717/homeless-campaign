package app;


public class SumData {
   // homeless population
   public int homelessTotal;

   // lga number
   public int lgaNumber;
   // year
   public int year;

   public double percentage;


  
   
   public SumData() {
   }

   
   public SumData(int homelessTotal, double percentage,int lgaNumber, int year) {
      this.homelessTotal = homelessTotal;
      this.percentage = percentage;
      this.lgaNumber = lgaNumber;
      this.year = year;
   }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GutPickMMA;

/**
 *
 * @author asina
 */
class RosterMetrics {
    
    private Integer ufcAtleteID;
    private int ranking;
    private String fighterName;
    private String weightDivision;
    private char fighterGender;
    private String fighterRecord;
    private String nickName;
    private double odds;
    private String placeOfBirth;
    private String dateOfBirth;
    private int age;
    private String height;
    private String weight;
    private String debut;
    private String winsViaKO;
    private String winsViaDecision;
    private String winsViaSubmission;

    public RosterMetrics() {
    }
    
    
    public RosterMetrics(int ranking, String fighterName, String weightDivision,
            char fighterGender, String fighterRecord, String nickName,
            double odds, String placeOfBirth, String dateOfBirth,
            int age, String height, String weight, String debut,
            String winsViaKO, String winsViaDecision, String winsViaSubmission) {

        this.ranking = ranking;
        this.fighterName = fighterName;
        this.weightDivision = weightDivision;
        this.fighterGender = fighterGender;
        this.fighterRecord = fighterRecord;
        this.nickName = nickName;
        this.odds = odds;
        this.placeOfBirth = placeOfBirth;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.debut = debut;
        this.winsViaKO = winsViaKO;
        this.winsViaDecision = winsViaDecision;
        this.winsViaSubmission = winsViaSubmission;
    }
    public RosterMetrics(int ranking, String fighterName, String weightDivision, String fighterRecord, String nickName,
            double odds, String placeOfBirth, String dateOfBirth,
            int age, String height, String weight, String debut,
            String winsViaKO, String winsViaDecision, String winsViaSubmission) {

        this.ranking = ranking;
        this.fighterName = fighterName;
        this.weightDivision = weightDivision;
        this.fighterRecord = fighterRecord;
        this.nickName = nickName;
        this.odds = odds;
        this.placeOfBirth = placeOfBirth;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.debut = debut;
        this.winsViaKO = winsViaKO;
        this.winsViaDecision = winsViaDecision;
        this.winsViaSubmission = winsViaSubmission;
    }
    

        public int getRanking() {
            return ranking;
        }

        public String getFighterName() {
            return fighterName;
        }

        public String getWeightDivision() {
            return weightDivision;
        }

        public char getFighterGender() {
            return fighterGender;
        }

        public String getFighterRecord() {
            return fighterRecord;
        }

        public String getNickName() {
            return nickName;
        }

        public double getOdds() {
            return odds;
        }

        public String getPlaceOfBirth() {
            return placeOfBirth;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public int getAge() {
            return age;
        }

        public String getHeight() {
            return height;
        }

        public String getWeight() {
            return weight;
        }

        public String getDebut() {
            return debut;
        }

        public String getWinsViaKO() {
            return winsViaKO;
        }

        public String getWinsViaDecision() {
            return winsViaDecision;
        }

        public String getWinsViaSubmission() {
            return winsViaSubmission;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }

        public void setFighterName(String fighterName) {
            this.fighterName = fighterName;
        }

        public void setWeightDivision(String weightDivision) {
            this.weightDivision = weightDivision;
        }

        public void setFighterGender(char fighterGender) {
            this.fighterGender = fighterGender;
        }

        public void setFighterRecord(String fighterRecord) {
            this.fighterRecord = fighterRecord;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public void setOdds(double odds) {
            this.odds = odds;
        }

        public void setPlaceOfBirth(String placeOfBirth) {
            this.placeOfBirth = placeOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public void setDebut(String debut) {
            this.debut = debut;
        }

        public void setWinsViaKO(String winsViaKO) {
            this.winsViaKO = winsViaKO;
        }

        public void setWinsViaDecision(String winsViaDecision) {
            this.winsViaDecision = winsViaDecision;
        }

        public void setWinsViaSubmission(String winsViaSubmission) {
            this.winsViaSubmission = winsViaSubmission;
        }
        public String getDetails() {
            return String.format("Name: %s\nOdds: %.2f", fighterName, odds);
        }
    
    @Override
    public String toString() {
        return "BettingConcept{" +
               "ranking=" + ranking +
               ", fighterName='" + fighterName + '\'' +
               ", weightDivision='" + weightDivision + '\'' +
               ", fighterGender=" + fighterGender +
               ", fighterRecord='" + fighterRecord + '\'' +
               ", nickName='" + nickName + '\'' +
               ", odds=" + odds +
               ", placeOfBirth='" + placeOfBirth + '\'' +
               ", dateOfBirth='" + dateOfBirth + '\'' +
               ", age=" + age +
               ", height='" + height + '\'' +
               ", weight='" + weight + '\'' +
               ", debut='" + debut + '\'' +
               ", winsViaKO='" + winsViaKO + '\'' +
               ", winsViaDecision='" + winsViaDecision + '\'' +
               ", winsViaSubmission='" + winsViaSubmission + '\'' +
               '}';
    }
    
}




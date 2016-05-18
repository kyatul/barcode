package com.bigdreams.barcodescanner;

import java.util.Date;

public class ScanDate {

	public static String getDate(){
		Date d=new Date();
		String [] ds=(d.toString()).split("\\s");
		String dt=ds[0]+" "+ds[1]+" "+ds[2]+" "+ds[3];
		return dt;
	}
	
	public static String getCountry(String t){
		
		if(t.substring(0,2).equals("00") || t.substring(0,2).equals("01")){
			return "US";
		}
		
		else if(t.substring(0,2).equals("30") || t.substring(0,2).equals("31")
				|| t.substring(0,2).equals("32")|| t.substring(0,2).equals("33")
				|| t.substring(0,2).equals("34")|| t.substring(0,2).equals("35")
				|| t.substring(0,2).equals("36")|| t.substring(0,2).equals("37")
				){
			return "France";
		}
		
		else if(t.substring(0,2).equals("40") || t.substring(0,2).equals("41")
				|| t.substring(0,2).equals("42")|| t.substring(0,2).equals("43")){
			return "Germany";
		}
		
		else if(t.substring(0,2).equals("80") || t.substring(0,2).equals("81")
				|| t.substring(0,2).equals("82")|| t.substring(0,2).equals("83")){
			return "Italy";
		}
		
		else if(t.substring(0,2).equals("45")){
			return "Japan";
		}
		
		else if(t.substring(0,2).equals("49")){
			return "Japan";
		}
		
		else if(t.substring(0,2).equals("46")){
			return "Russia";
		}
		
		else if(t.substring(0,2).equals("50")){
			return "UK";
		}
		
		else if(t.substring(0,2).equals("54")){
			return "Belgium & Luxembourg";
		}
		
		else if(t.substring(0,2).equals("57")){
			return "Denmark";
		}
		
		else if(t.substring(0,2).equals("64")){
			return "Finland";
		}
		else if(t.substring(0,2).equals("69")){
			return "China";
		}
		else if(t.substring(0,2).equals("70")){
			return "Norway";
		}
		else if(t.substring(0,2).equals("73")){
			return "Sweden";
		}
		
		//
		
		else if(t.substring(0,2).equals("84")){
			return "Spain";
		}
		else if(t.substring(0,2).equals("87")){
			return "Netherlands";
		}
		else if(t.substring(0,2).equals("90")){
			return "Austria";
		}
		else if(t.substring(0,2).equals("91")){
			return "Austria";
		}
		else if(t.substring(0,2).equals("93")){
			return "Austraila";
		}
		else if(t.substring(0,2).equals("94")){
			return "New Zealand";
		}
		
		
		else if(t.substring(0,2).equals("76")){
			return "Schweiz, Suisse, Svizzera";
		}
		
		
		
		
		switch(Integer.parseInt(t)){
		
		case 380:	  return "Bulgaria";
		case 383:	  return "Slovenija";
		case 385:	  return "Croatia";
		case 387:	  return "BIH (Bosnia-Herzegovina)";
		case 389:	  return "Montenegro";
		case 440:	  return"Germany";
		case 470:	  return "Kyrgyzstan";
		case 471:	  return "Taiwan";
		case 474:	  return "Estonia";
		case 475:	  return "Latvia";
		case 476:	  return "Azerbaijan";
		case 477:	  return "Lithuania";
		case 478:	  return "Uzbekistan";
		case 479:	  return "Sri Lanka";
		case 480:	  return "Philippines";
		case 481:	  return "Belarus";
		case 482:	  return "Ukraine";
		case 484:	  return "Moldova";
		case 485:	  return "Armenia";
		case 486:	  return "Georgia";
		case 487:	  return "Kazakstan";
		case 488:	  return "Tajikistan";
		case 489:	  return "Hong Kong";
		case 520:	  return "Association Greece";
		case 521:	  return "Association Greece";
		case 528:	  return "Lebanon";
		case 529:	  return "Cyprus";
		case 530:	  return "Albania";
		case 531:	  return "MAC (FYR Macedonia)";
		case 535:	  return "Malta";
		case 539:	  return "Ireland";
		case 560:	  return "Portugal";
		case 569:	  return "Iceland";
		case 590:	  return "Poland";
		case 594:	  return "Romania";
		case 599:	  return "Hungary";
		case 600:	  return "South Africa";
		case 601:	  return "South Africa";
		case 603:	  return "Ghana";
		case 604:	  return "Senegal";
		case 608:	  return "Bahrain";
		case 609:	  return "Mauritius";
		case 611:	  return "Morocco";
		case 613:	  return "Algeria";
		case 615:	  return "Nigeria";
		case 616:	  return "Kenya";
		case 618:	  return "Ivory Coast";
		case 619:	  return "Tunisia";
		case 620:	  return "Tanzania";
		case 621:	  return "Syria";
		case 622:	  return "Egypt";
		case 623:	  return "Brunei";
		case 624:	  return "Libya";
		case 625:	  return "Jordan";
		case 626:	  return "Iran";
		case 627:	  return "Kuwait";
		case 628:	  return "Saudi Arabia";
		case 629:	  return "Emirates";
		case 729:	  return "Israel";
		case 740:	  return "Guatemala";
		case 741:	  return "El Salvador";
		case 742:	  return "Honduras";
		case 743:	  return "Nicaragua";
		case 744:	  return "Costa Rica";
		case 745:	  return "Panama";
		case 746:	  return "Republica Dominicana";
		case 750:	  return "Mexico";
		case 754:     return "Canada";
		case 755:     return "Canada";
		case 759:	  return "Venezuela";
		case 770:     return "Colombia";
		case 771:     return "Colombia";
		case 773:	  return "Uruguay";
		case 775:	  return "Peru";
		case 777:	  return "Bolivia";
		case 778:     return "Argentina";
		case 779:     return "Argentina";
		case 780:	  return "Chile";
		case 784:	  return "Paraguay";
		case 786:	  return "Ecuador";
		case 789:     return "Brasil";
		case 790:     return "Brasil";
		case 850:	  return "Cuba";
		case 858:	  return "Slovakia";
		case 859:	  return "Czech";
		case 860:	  return "Serbia";
		case 865:	  return "Mongolia";
		case 867:	  return "North Korea";
		case 868:     return "Turkey";
		case 869:     return "Turkey";
		case 880:	  return "South Korea";
		case 884:	  return "Cambodia";
		case 885:	  return "Thailand";
		case 888:	  return "Singapore";
		case 890:	  return "India";
		case 893:	  return "Vietnam";
		case 896:	  return "Pakistan";
		case 899:	  return "Indonesia";		
		case 955:	  return "Malaysia";
		case 958:	  return "Macau";
		case 977:	  return "International Standard Serial Number for periodicals(ISSN)";
		case 978:	  return "International Standard Book Number(ISBN)";
		case 979:	  return "International Standard Book Number(ISBN)";
		}
		
		return "No Match Found";
	}
}

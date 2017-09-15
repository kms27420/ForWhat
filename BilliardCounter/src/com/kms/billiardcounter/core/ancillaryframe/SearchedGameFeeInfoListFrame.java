package com.kms.billiardcounter.core.ancillaryframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import com.kms.billiardcounter.core.string.DateAndTableNumberSentenceConvertor;
import com.kms.billiardcounter.dao.search.SearchedGameFeeInfoLoader;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.support.gamefeeinfo.GameFeeInfo;

/**
 * 
 * 검색한 게임 목록을 보여주는 프레임
 * 
 * @author Kwon
 *
 */
public class SearchedGameFeeInfoListFrame extends JFrame {

	public SearchedGameFeeInfoListFrame( String searchSentence ) {
		
		initThisFrame();
		
		String[] dateAndTableNumber = DateAndTableNumberSentenceConvertor.convertSearchSentenceIntoDateAndTableNumber( searchSentence );
		ArrayList<GameFeeInfo> searchedGameFeeInfoList = SearchedGameFeeInfoLoader.getSearchedByDateAndTableNumberGameFeeInfoList( dateAndTableNumber[0], dateAndTableNumber[1] );
		
		setThisFrameTitle( dateAndTableNumber[0], dateAndTableNumber[1] );
		changeFramePropertyToFitListCount( searchedGameFeeInfoList );
		
		add( createSearchedGameFeeInfoListScrollPane( searchedGameFeeInfoList ) );
		
		setVisible( true );
		
	}
	
	private void initThisFrame() {
		
		setLayout( new GridLayout( 1, 1 ) );
		
		setPreferredSize( new Dimension( 1000, 700 ) );
		setResizable( false );
		
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		
	}
	
	private void setThisFrameTitle( String date, String tableNumber ) {
		
		if( date == null || tableNumber == null ) {
			
			setTitle("올바르지 않은 검색어 형태 오류");
			
			return;
		
		}
		
		setTitle( "20" + date.substring( 0, 2 ) + "년 " + date.substring( 2, 4 ) + "월 " + date.substring( 4, 6 ) + "일, " + tableNumber + "번 테이블 검색 결과" );
		
	}
	
	private void changeFramePropertyToFitListCount( ArrayList<GameFeeInfo> searchedGameFeeInfoList ) {
		
		Dimension frameSize = new Dimension( 1000, ( searchedGameFeeInfoList.size() + 1 ) * 60 + 50 );
		
		if( frameSize.height > 1000 ) {
			
			frameSize.setSize( 1000, 1000 );
			
		} else if ( searchedGameFeeInfoList.size() == 0 ) {
			
			frameSize.setSize( 500, 400 );
			
		}
		
		setSize( frameSize );
		
		setLocation( Toolkit.getDefaultToolkit().getScreenSize().width / 2 - frameSize.width / 2
				, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - frameSize.height / 2 );
		
	}
	
	private JScrollPane createSearchedGameFeeInfoListScrollPane( ArrayList<GameFeeInfo> searchedGameFeeInfoList ) {
		
		JScrollPane searchedGameFeeInfoListScrollPane = new JScrollPane(); 
		
		JPanel searchedGameFeeInfoListPanel = new JPanel();
		
		if( searchedGameFeeInfoList.size() == 0 ) {
			
			JLabel alignLabel = new JLabel( "검색 결과가 없습니다." );
			
			alignLabel.setOpaque( true );
			alignLabel.setBackground( Color.WHITE );
			alignLabel.setFont( FontProvider.getDefaultFont() );
			alignLabel.setHorizontalAlignment( JLabel.CENTER );
			
			searchedGameFeeInfoListPanel.setLayout( new GridLayout( 1, 1 ) );
			searchedGameFeeInfoListPanel.add( alignLabel );
			
			searchedGameFeeInfoListScrollPane.setViewportView( searchedGameFeeInfoListPanel );
			
			return searchedGameFeeInfoListScrollPane;
			
		}
		
		JLabel[] titleLabel = new JLabel[8];
		
		titleLabel[0] = new JLabel( "날짜" );
		titleLabel[1] = new JLabel( "시작 시간" );
		titleLabel[2] = new JLabel( "종료 시간" );
		titleLabel[3] = new JLabel( "게임 번호" );
		titleLabel[4] = new JLabel( "테이블 번호" );
		titleLabel[5] = new JLabel( "사용 시간" );
		titleLabel[6] = new JLabel( "요금" );
		titleLabel[7] = new JLabel( "계산 여부" );
		
		for( int i = 0; i < 8; i++ ) {
			
			titleLabel[i].setHorizontalAlignment( JLabel.CENTER );
			titleLabel[i].setBorder( new BevelBorder(BevelBorder.RAISED) );
			titleLabel[i].setFont( FontProvider.getDefaultFont() );
			
			searchedGameFeeInfoListPanel.add( titleLabel[i] );
			
		}
		
		for( int i = 0; i < searchedGameFeeInfoList.size(); i++ ) {
			
			JLabel[] contentsLabel = new JLabel[8];
			
			contentsLabel[0] = new JLabel( searchedGameFeeInfoList.get(i).getDate() );
			contentsLabel[1] = new JLabel( searchedGameFeeInfoList.get(i).getStartTime() );
			contentsLabel[2] = new JLabel( searchedGameFeeInfoList.get(i).getEndTime() );
			contentsLabel[3] = new JLabel( "" + searchedGameFeeInfoList.get(i).getGameNumber() + " 번" );
			contentsLabel[4] = new JLabel( "" + searchedGameFeeInfoList.get(i).getTableNumber() + " 번" );
			contentsLabel[5] = new JLabel( "" + searchedGameFeeInfoList.get(i).getUsedTime() + " 분" );
			contentsLabel[6] = new JLabel( "" + searchedGameFeeInfoList.get(i).getFee() +" 원" );
			contentsLabel[7] = new JLabel( "" + searchedGameFeeInfoList.get(i).getIsPaid() );
			
			for( int j = 0; j < 8; j++ ) {
				
				contentsLabel[j].setOpaque( true );
				contentsLabel[j].setBackground( Color.WHITE );
				contentsLabel[j].setHorizontalAlignment( JLabel.CENTER );
				contentsLabel[j].setBorder( new BevelBorder(BevelBorder.RAISED) );
				contentsLabel[j].setFont( FontProvider.getDefaultFont() );
				
				searchedGameFeeInfoListPanel.add( contentsLabel[j] );
				
			}
			
		}
		
		searchedGameFeeInfoListPanel.setLayout( new GridLayout( searchedGameFeeInfoList.size() + 1, 8 ) );
		searchedGameFeeInfoListPanel.setPreferredSize( new Dimension( 0, ( searchedGameFeeInfoList.size() + 1 ) * 60 ) );
		
		searchedGameFeeInfoListScrollPane.setViewportView( searchedGameFeeInfoListPanel );
		
		return searchedGameFeeInfoListScrollPane;
		
	}
	
}

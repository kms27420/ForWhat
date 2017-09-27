package com.kms.billiardcounter.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import com.kms.billiardcounter.database.game_list.GameListLoader;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.size.DeviceSize;
import com.kms.billiardcounter.size.FrameSize;
import com.kms.billiardcounter.support.GameFeeInfo;
import com.kms.billiardcounter.support.SearchKey;

/**
 * 
 * 검색한 게임 목록을 보여주는 프레임
 * 
 * @author Kwon
 *
 */
public class SearchResultFrame extends JFrame {

	private static final SearchResultFrame INSTANCE = new SearchResultFrame();
	
	private SearchResultFrame() {
		
		setResizable( false );
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		
		getContentPane().setLayout( new GridLayout( 1, 1 ) );
		
	}
	
	private void setThisFrameTitle( SearchKey searchKey ) {
		
		if( !searchKey.getIsValid() ) {
			
			setTitle("올바르지 않은 검색어 형태 오류");
			
			return;
		
		}
		
		setTitle( "20" + searchKey.getDate().substring( 0, 2 ) + "년 " + searchKey.getDate().substring( 2, 4 ) + "월 " + searchKey.getDate().substring( 4, 6 ) + "일, " + searchKey.getTableNumber() + "번 테이블 검색 결과" );
		
	}
	
	private void changeFramePropertyToFitListCount( ArrayList<GameFeeInfo> searchedGameFeeInfoList ) {
		
		Dimension frameSize = new Dimension( FrameSize.getSearchResultFrameMaxSize().width, ( searchedGameFeeInfoList.size() + 1 ) * 60 + 50 );
		
		if( frameSize.height > 1000 ) {
			
			frameSize.setSize( FrameSize.getSearchResultFrameMaxSize() );
			
		} else if ( searchedGameFeeInfoList.size() == 0 ) {
			
			frameSize.setSize( FrameSize.getSearchResultFrameMinSize() );
			
		}
		
		setPreferredSize( frameSize );
		pack();
		setLocation( ( DeviceSize.getScreenSize().width - frameSize.width ) / 2 , ( DeviceSize.getScreenSize().height - frameSize.height ) / 2 );
		
	}
	
	private JScrollPane createSearchedGameFeeInfoListScrollPane( ArrayList<GameFeeInfo> searchedGameFeeInfoList ) {
		
		JScrollPane searchedGameFeeInfoListScrollPane = new JScrollPane(); 
		
		JPanel searchedGameFeeInfoListPanel = new JPanel();
		
		if( searchedGameFeeInfoList.size() == 0 ) {
			
			JLabel alertLabel = new JLabel( "검색 결과가 없습니다." );
			
			alertLabel.setOpaque( true );
			alertLabel.setBackground( Color.WHITE );
			alertLabel.setFont( FontProvider.getDefaultFont() );
			alertLabel.setHorizontalAlignment( JLabel.CENTER );
			
			searchedGameFeeInfoListPanel.setLayout( new GridLayout( 1, 1 ) );
			searchedGameFeeInfoListPanel.add( alertLabel );
			
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
		
		for( int index = 0; index < 8; index++ ) {
			
			titleLabel[index].setHorizontalAlignment( JLabel.CENTER );
			titleLabel[index].setBorder( new BevelBorder(BevelBorder.RAISED) );
			titleLabel[index].setFont( FontProvider.getDefaultFont() );
			
			searchedGameFeeInfoListPanel.add( titleLabel[index] );
			
		}
		
		for( int creatorIndex = 0; creatorIndex < searchedGameFeeInfoList.size(); creatorIndex++ ) {
			
			JLabel[] contentsLabel = new JLabel[8];
			
			contentsLabel[0] = new JLabel( searchedGameFeeInfoList.get(creatorIndex).getDate() );
			contentsLabel[1] = new JLabel( searchedGameFeeInfoList.get(creatorIndex).getStartTime() );
			contentsLabel[2] = new JLabel( searchedGameFeeInfoList.get(creatorIndex).getEndTime() );
			contentsLabel[3] = new JLabel( "" + searchedGameFeeInfoList.get(creatorIndex).getGameNumber() + " 번" );
			contentsLabel[4] = new JLabel( "" + searchedGameFeeInfoList.get(creatorIndex).getTableNumber() + " 번" );
			contentsLabel[5] = new JLabel( "" + searchedGameFeeInfoList.get(creatorIndex).getUsedTime() + " 분" );
			contentsLabel[6] = new JLabel( "" + searchedGameFeeInfoList.get(creatorIndex).getFee() +" 원" );
			contentsLabel[7] = new JLabel( "" + searchedGameFeeInfoList.get(creatorIndex).getIsPaid() );
			
			for( int initorIndex = 0; initorIndex < 8; initorIndex++ ) {
				
				contentsLabel[initorIndex].setOpaque( true );
				contentsLabel[initorIndex].setBackground( Color.WHITE );
				contentsLabel[initorIndex].setHorizontalAlignment( JLabel.CENTER );
				contentsLabel[initorIndex].setBorder( new BevelBorder(BevelBorder.RAISED) );
				contentsLabel[initorIndex].setFont( FontProvider.getDefaultFont() );
				
				searchedGameFeeInfoListPanel.add( contentsLabel[initorIndex] );
				
			}
			
		}
		
		searchedGameFeeInfoListPanel.setLayout( new GridLayout( searchedGameFeeInfoList.size() + 1, 8 ) );
		searchedGameFeeInfoListPanel.setPreferredSize( new Dimension( 0, ( searchedGameFeeInfoList.size() + 1 ) * 60 ) );
		
		searchedGameFeeInfoListScrollPane.setViewportView( searchedGameFeeInfoListPanel );
		
		return searchedGameFeeInfoListScrollPane;
		
	}
	
	public static void showOnScreen( String searchSentence ) {
		
		SearchKey searchKey = new SearchKey( searchSentence );
		ArrayList<GameFeeInfo> searchedGameFeeInfoList = GameListLoader.getSearchedGameFeeInfoList( searchKey );
		
		INSTANCE.setThisFrameTitle( searchKey );
		INSTANCE.changeFramePropertyToFitListCount( searchedGameFeeInfoList );
		
		INSTANCE.getContentPane().removeAll();
		INSTANCE.getContentPane().add( INSTANCE.createSearchedGameFeeInfoListScrollPane( searchedGameFeeInfoList ) );
		
		INSTANCE.setVisible( true );
		
	}
	
}
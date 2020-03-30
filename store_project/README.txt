	*** update: 2019.02.13.
	
	*** '0. 종료'를 이용해서 정상적인 종료를 하여야 user.ser에 저장을 수행하고,
		다음 프로그램 실행 때 user.ser에서 제대로 읽어온다.
		프로그램 강제 종료시에는 에러 발생.

	Main	
		- Main의 요소.	
		Scanner keyin
		ComputerStore danawa (예시 danawa 컴퓨터 사이트)
		UserManager uMgr
		
		- Main의 method
		부품(CPU 등) 파일 읽기: .ser 파일을 load함.
		user.ser 읽기: 파일의 내용을 uMgr에 가져옴. user.ser 파일은 프로그램이 종료하기 전에
			uMgr 클래스 객체를 담아놓은 파일.

		로그인: uMgr에 알맞는 id가 입력되지 않으면 재시도.(3회 까지)
		회원가입: uMgr.signUp()
		UserManager 저장: 종료(0)를 선택하면 uMgr을 user.ser 파일에 저장하여 user들의 정보를
			파일에 저장하여 다음 프로그램이 실행될 때에도 정보가 남아있다.
			
	ComputerStore
		- ComputerStore의 요소.
		storeList: vector를 이용해서 각 부품들을 관리하는 store 리스트를 가짐.
		
		- ComputerStore의 method
		불러오기: 각 부품의 .ser파일을 읽어서 저장.
		로그인 메뉴: 로그인을 했다면 부품 구매 및 조립이 가능하다.
		부품 구매: 어떤 부품을 살지 선택을 하고 그에 맞는 부품의 store에서 buy()를 실행.
		
	UserManager extends Manager
		- UserManager의 요소.
		serialVersionUID: 직렬화(.ser 파일에 저장)를 위해 필요한 ID
		ArrayList<Manageable>: User의 인터페이스가 Manageable
		
		- UserManager의 method
		user찾기: Manager의 findObjects()를 이용.
		signUp: ID중복을 허락하지 않으며 중복이 아니면 바로 new User(id)로 생성
		createFile: 처음에 프로그램이 실행될 때 user에 대한 정보가 없으므로 
			아무 내용도 없는 user.ser 파일을 생성한다.
		printUsers: user.ser에서 데이터를 제대로 읽어 왔는지 확인하기 위한
			등록된 아이디 목록 출력.
			
	Manager<Manageable>
		- Manager의 요소
		serialVersionUID: 직렬화(.ser 파일에 저장)를 위해 필요한 ID
		ArrayList<T> mList
		
		- Manager의 method
		openFile: file의 Scanner를 리턴
		readAll: file을 읽어서 mList에 저장.
		printAll: mList 출력
		printSimpleAll: 간단한 출력
		findObjects: mList에서 keyword를 비교한 후 결과(여러 개) resultList를 리턴
		
	Store<Sellable> extends Manager
		- Store의 요소
		상속받는 Manager의 mList 이용.
		
		- Store의 method
		판매: user에게 item을 판매
		검색: 키워드가 포함된 요소들만 모아서 새로운 ArrayList를 반환.
		리스트 생성: 전에는 파일을 read하면서 생성했지만 웹 크롤링을 이용하기 위해
			read가 아닌 다른 방식으로 리스트 생성.
		저장: 만들어진 리스트를 .ser파일 형태로 저장.
		불러오기: .ser파일을 프로그램에 필요한 객체에 대입.
	
	CPUCrawler, Crawler
		url을 이용해 필요한 데이터를 뽑아서 저장. 부품마다 형식이 조금씩 다르기 때문에
		조금씩 변형된 크롤러가 필요.
		
	product Package
		CPU, RAM(메모리), MB(메인보드), HDD(하드디스크), VGA(그래픽카드),
		PSU(파워서플라이), Case: 컴퓨터 조립에 필요한 7가지 부품.
		
자바 - 콘솔 기반 전자책 댓글 게시판 프로젝트

기능 구현: eBook 플랫폼의 책 목록, 책 상세 정보 조회, 댓글 작성 및 조회 기능을 구현.  UI 구현: 사용자와 상호작용하는 댓글 UI, 책 UI 구현.  DB 연동: Oracle DB와 연동하여 책 및 댓글 데이터를 저장하고 조회하는 기능 구현.  서비스 계층: 각 기능을 서비스 계층을 통해 처리하고, DAO에서 DB와의 직접적인 상호작용을 관리
책 관련 기능:

책 목록 조회 (BookService): BookDAO를 통해 책 정보를 DB에서 조회하고 반환.

책 상세 정보 조회 (BookService): 특정 책 ID로 책 정보를 DB에서 조회.

책 검색 기능 추가 (BookService): 제목으로 책을 검색하여 관련 책 목록 반환.

댓글 관련 기능:

댓글 조회 (CommentService): 특정 책에 달린 댓글을 조회하여 화면에 출력.

댓글 작성 (CommentService): 사용자가 책에 댓글을 작성할 수 있도록 기능 구현.

댓글 수정 및 삭제 (CommentService, CommentDAO): 사용자가 작성한 댓글을 수정하거나 삭제할 수 있는 기능 구현.

UI 구현:

BookUI: 책 목록과 상세 정보 조회 UI.

CommentUI: 댓글 목록 조회 및 댓글 작성 UI.

책 Viewer UI: 책을 읽고 페이지 전환 및 자동 댓글 UI 전환 기능 구현.

DB 설계:

ebook_books 테이블: 책 정보를 저장.

comments 테이블: 각 책에 달린 댓글을 저장.

댓글 작성 및 조회 기능을 위한 SQL문 작성.

DB 연동을 위한 DAO (BookDAO, CommentDAO) 설계.

기타:

DB 연동을 위해 Oracle DB와 JDBC 연결을 처리하는 ConnectionFactory 및 JDBCClose 클래스 사용.

BaseUI를 상속하여 UI 공통 기능 구현.

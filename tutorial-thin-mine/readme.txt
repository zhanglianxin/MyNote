*******************************************************************************
      TERASOLUNA Server Framework for Java (Web��)
      �`���[�g���A���v���W�F�N�g �����菇

      Copyright 2007-2009 NTT DATA Corporation.
*******************************************************************************


��  �T�v�F

  ����readme�́ATERASOLUNA Server Framework for Java (Web��) 
  �̃`���[�g���A���v���W�F�N�g�𓱓�����菇���ł��B
  ���L�菇�ɏ]�����ƂŁA�`���[�g���A���v���W�F�N�g�����s���邱�Ƃ��ł��܂��B 
  
  

��  �O������F

  �������ɂ́A���炩���߉��L�̂��̂��p�ӂ���Ă���K�v������܂��B 

  �EJava 2 Runtime Environment Standard Edition 1.5.0
  �EEclipse SDK 3.2.2 + WTP 1.5.5
  �EWebAP�T�[�o�FApache Tomcat 5.5.23
  �EApache Ant 1.6.5 (Eclipse��Ant�v���O�C���݂̂ł���)
  
  ���L����Ă���o�[�W���������ɓ���m�F���s���Ă��܂����A���̃o�[�W�����ȊO�̊�
  ���ł̓���𐧌�������̂ł͂���܂���B
  �܂��A�����̃C���X�g�[���y�ѐݒ�̎菇�ɂ��ẮA�ʓrWeb��̗��p�K�C�h����
  �Q�Ƃ��Ă��������B 
  


��  Ant�^�X�N�ꗗ�F

  �v���W�F�N�g�́u\ant�v�t�H���_�ɂ́Aant�^�X�N���L�q���ꂽ�ubuild.xml�v�����݂�
  �܂��B�ȉ���ant�^�X�N�̈ꗗ�ł��B 

  �Eclean
      �쐬����war�t�@�C���A�R���p�C�����ꂽ�N���X�t�@�C���̍폜���s���B
  �Ecompile
      �R���p�C�����s���B
  �Enative2ascii
      native2ascii�ɂăv���p�e�B�t�@�C���̃R�[�h�ϊ����s���B
  �Edeploy
      �v���W�F�N�g�̃f�v���C���s���B
  �EcreateJavaDoc
      JavaDoc�̐������s���B



��  �T�[�o�[�̒ǉ��iWTP���j�F

  Eclipse��WTP�v���O�C������������Ă���ꍇ�A�ȉ��̎菇�ŃT�[�o�[��ǉ����܂��B 

  1.Eclipse��ʂɂāu�E�B���h�E�v���u�r���[�̕\���v���u���̑��v�����s���A�u�T�[
    �o�[�v��I�����uOK�v���N���b�N���܂��B 
  2.�T�[�o�[�r���[�ŉE�N���b�N�u�V�K�v���u�T�[�o�[�v�����s���܂��B 
  3.�uApache�v���uTomcat v5.5 �T�[�o�[�v��I�����A�u���ցv���N���b�N���܂��B 
  4.�uTomcat�v��I�����A�u���ցv���N���b�N���܂��B 
  5.���[���̊��ɍ������uTomcat �C���X�g�[����f�B���N�g���[�v��I�����܂��B
  6.�u�I���v���N���b�N���܂��B 



��  �v���W�F�N�g�̎��s�i���ʁj�F

  �`���[�g���A���v���W�F�N�g��Eclipse�ɃC���|�[�g���܂��B�܂��AH2Database�ɏ����f�[�^
  ��ݒ肵�܂��B

  �@ZIP�t�@�C���̓W�J
  
    terasoluna-server4jweb-tutorial_(�o�[�W�����ԍ�).zip��C�ӂ̃t�H���_�ɓW�J���܂��B 

    �W�J���ꂽterasoluna-server4jweb-tutorial_(�o�[�W�����ԍ�).zip�̃t�H���_����
    �ututorial-thin�v�ł��邱�Ƃ��m�F���܂��B
    �W�J�c�[����W�J�̂������ɂ���ẮA�t�H���_����
    �uterasoluna-server4jweb-tutorial_(�o�[�W�����ԍ�)�v�ƂȂ�ꍇ������܂����A
    �t�H���_�����蓮�Łututorial-thin�v�ɕύX���Ă��������B 

  �AEclipse�ւ̃C���|�[�g
  
    1.Eclipse��ʂɂāu�t�@�C���v���u�C���|�[�g�v��I�����܂��B
    2.�u�����v���W�F�N�g�����[�N�X�y�[�X�ցv��I�����u���ցv���N���b�N���܂��B
    3.�u���[�g�E�f�B���N�g���[�̑I���v�Ƀ`�F�b�N����������ԂŁu�Q�Ɓv���N���b�N
      ���A�u�����N�v���W�F�N�g��W�J�����e�t�H���_���w�肵�܂��B 
    4.�ututorial-thin�v�Ƀ`�F�b�N�������Ă��邱�Ƃ��m�F���A�u�I���v���N��
      �b�N���܂��B 

    �G���[���o��ꍇ�́u�����l�v���Q�Ƃ��A�ݒ��ύX���Ă��������B 

  �BH2Database�ɏ����f�[�^��ݒ�

    1.�`���[�g���A���v���W�F�N�g���́uh2db/h2db_start.bat�v�����s���܂��B
      �u���E�U���N�����AH2Database Console�̃��O�C����ʂ��\������܂��B
    2.�����āA�uh2db/h2db_init.bat�v�����s���܂��B�iscript�����s���ꏉ���f�[�^���ݒ肳��܂��j
    3.�f�[�^�x�[�X�ւ̐ڑ�
      H2Database Console�̃��O�C����ʂŁA�ȉ�����͂��܂��B 

      �ۑ��ϐݒ�     �F Generic H2 (Server)
      �h���C�o�N���X �F org.h2.Driver
      JDBC URL       �F jdbc:h2:tcp://localhost/~/terasoluna
      User           �F sa
      Password       �F 

    4.�f�[�^�x�[�X�̊m�F
      �f�[�^�x�[�X�̐ڑ���A��ʍ��̃e�[�u���ꗗ�Ɉȉ������݂��邱�Ƃ��m�F���܂��B 
      
      �e�[�u����  �F�gUSERLIST�h�A
      �J����      �F�gID�h�A�gNAME�h�A�gAGE�h�A�gBIRTH�h
      
      ���̂܂�H2Database Console�͋N�������܂܂ɂ��Ă����܂��B

      ��1�̌�AH2Database Console���N�����Ȃ��ꍇ�́A�uh2db/h2db_console.bat�v�����s���Ă��������B

  
��  �v���W�F�N�g�̎��s�iWTP���j�F
 
  WTP�v���O�C���𗘗p����ꍇ�A�ȉ��̎菇�Ńv���W�F�N�g�����s���܂��B

  �B�T�[�o�[�ւ̒ǉ�
  
    �T�[�o�[�r���[�Łututorial-thin�v�̃v���W�F�N�g��ǉ����܂��B
    
  �CTomcat�̋N������сA���s�m�F
  
    1.�T�[�o�[���n�����܂��B 
      �E�T�[�o�[�n�����ɃG���[�_�C�A���O���\�������ꍇ�́A�u�����l�v���Q�Ƃ��A
        �ݒ��ύX���Ă��������B 
    2.http://localhost:8080/tutorial-thin�ɃA�N�Z�X���܂��B
    3.�uUserName�v�̓��̓t�B�[���h�����݂��郍�O�I����ʂ��\�������ΐ����ł��B
      �E ���O�C���̃��[�U���͔C�ӂ̒l����͂��܂��B
  
  
  
��  �v���W�F�N�g�̎��s�i��WTP���j�F

  WTP�v���O�C���𗘗p���Ȃ��ꍇ�A�ȉ��̎菇�Ńv���W�F�N�g�����s���܂��B 


  �Bant�^�X�N�̎��s
  
    1.�v���W�F�N�g�́u/ant/build.properties�v�����[���̊��ɍ������l�ɏ�������
      �܂��B 
      �E�ڍׂ�build.properties���Q�Ƃ��Ă��������B���Ƀp�X�֘A�Ɋւ��Ă͏\���m�F
        ���Ă��������B 
    2.�ubuild.properties�v�̏C����A�u/ant/build.xml�v���E�N���b�N��ant�^�X�N��
      �udeploy�v��I�����Ď��s���܂��B 

  �CTomcat�̋N������сA���s�m�F
  
    1.�f�v���C���Tomcat���N�����܂��B 
    2.http://localhost:8080/<context.name>/�ɃA�N�Z�X���܂��B 
      �E�u<context.name>�v�́Abuild.properties���ɋL�q�����l�ł���A�f�t�H���g��
        tutorial-thin�ƂȂ��Ă��܂��B    
    3.�uUserName�v�̓��̓t�B�[���h�����݂��郍�O�I����ʂ��\�������ΐ����ł��B
      �E ���O�C���̃��[�U���͔C�ӂ̒l����͂��܂��B

    
    
��  ���l�F

  �C���|�[�g��ɃG���[���o��ꍇ
  �E�u�C���|�[�g���ꂽ �`�` �͌�����܂���B�v
    ��WEB-INF/lib�t�H���_�ȉ��ɂ���Web�A�v���P�[�V�������C�u�����[���F�������
      �Ȃ���Ԃł��BEclipse���ċN�����邱�Ƃŉ������܂��B 
      
  �E�u�`�` �������ł��܂���B�v
    ��JSP�̃X�N���v�g�ϐ���F���ł��ĂȂ����߂ɏo�Ă���G���[�ł��B
      Eclipse��ʂɂāu�E�B���h�E�v���u�ݒ�v���u���؁v��I�����A�u�v���W�F�N�g
      �ł����̐ݒ���㏑�����\�ɂ���v�Ƀ`�F�b�N�����uOK�v�������܂��B
      ���̌�v���W�F�N�g���u�E�N���b�N�v���u�v���p�e�B�v���u���؁v��I�����A
      �u���؂̐ݒ�̏㏑���v�Ƀ`�F�b�N�����A�u�I�����ꂽ�o���f�[�^�[��Xxxx
      �i�v���W�F�N�g���j�����؂̍ۂɎ��s����܂�:�v�́A�uJSP�\���o���f�[�^�[�v��
      �`�F�b�N���͂����܂��B���̌�AEclipse���ċN������Ɖ������܂��B
    
  �T�[�o�[�n����ɃG���[���o��ꍇ
  �E�u���\�[�X �`�`�̓t�@�C���E�V�X�e���Ɠ������Ƃ�Ă��܂���B�v
    ��Eclipse��̃L���b�V���Ǝ��t�@�C���̓��������Ă��Ȃ���Ԃł��B�Y���̃v��
      �W�F�N�g���u�E�N���b�N�v���u�X�V�v����Ɖ������܂��B


-------------------------------------------------------------------------------
Copyright 2007-2009 NTT DATA Corporation.

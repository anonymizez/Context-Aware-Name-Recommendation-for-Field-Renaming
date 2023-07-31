package renamefield.projectPath;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;

public class CompletionPath2 {
	static List<SaveData> allDataSave = new ArrayList<>();// 保存所有数据
	static List<SaveData> allDataSave1 = new ArrayList<>();// 保存所有数据
	static List<FieldDeclaration> fieldDeclarations = new ArrayList<FieldDeclaration>();// 所有的field
	static List<String> commitId = new ArrayList<>();
	static List<String> classPath = new ArrayList<>();
	static List<String> projectName = new ArrayList<>();// 项目名
	static List<String> beforeField = new ArrayList<>();
	static List<String> afterField = new ArrayList<>();

	static List<String> saveClassName = new ArrayList<>();
	static List<String> saveUpFieldName = new ArrayList<>();
	static List<String> saveAfterJavadoc = new ArrayList<>();
	static List<String> saveafterModifier = new ArrayList<>();
	static List<String> saveafterType = new ArrayList<>();
	static List<String> savebeforeName = new ArrayList<>();
	static List<String> saveExpression = new ArrayList<>();
	static List<String> saveafterName = new ArrayList<>();
	static List<String> saveDownFieldName = new ArrayList<>();
	static List<String> saveSameType = new ArrayList<>();

	static String reachFile = "";
	static List<TypeDeclaration> typeDeclarations = new ArrayList<TypeDeclaration>();
	static List<MethodDeclaration> methodDeclarations = new ArrayList<MethodDeclaration>();
	static List<String> projectJavaList = new ArrayList<>();
	static List<String> projectJavaListBefore = new ArrayList<>();
	static List<FieldAccess> fieldAccess = new ArrayList<FieldAccess>();

	static List<SaveData> noPathFieldDataSaves = new ArrayList<>();

	public static void main(String[] args) throws IOException, InterruptedException, NoHeadException, GitAPIException {
		boolean flags = false;
		int sums = 0;
		int nums = -1;// 记录数据
		String saveCSV = "D:\\BIT_Report\\dataSource2\\noPathField.csv";
		
//		String existCSV = "D:\\BIT_Report\\dataSource\\existPathField.csv";
		String csvPath = "D:\\BIT_Report\\testRule\\test.csv";
//		String csvPath = "D:\\BIT_Report\\dataSource\\noField.csv";
		
		String directoryPath = "D:\\BIT_Report\\dataSource2";
		readFile(csvPath);
		File directory = new File(directoryPath);
		File[] files = directory.listFiles();
		String javaPath = "";
		boolean flag = false;
		for (int i = 0; i < commitId.size(); i++) {
			nums += 1;
			String dataSavePath = nums + "_" + afterField.get(i) + "_" + commitId.get(i) + ".java";
			boolean contains = Arrays.asList(files).contains(dataSavePath);
			if (contains) {
//				System.out.println(file.getName());
			} else {
				String gitString = "D:\\AllProject\\dataset\\" + projectName.get(i) + "\\.git";
				String projectname = "D:\\AllProject\\dataset\\" + projectName.get(i);
				String cmd = "cmd /c D: && cd " + projectname + " " + "&& git reset --hard" + " " + commitId.get(i);
				Runtime run = Runtime.getRuntime();
				try {
					Process process = run.exec(cmd);
					Thread.sleep(1000);
//			            process.waitFor();
				} catch (IOException e) {
					e.printStackTrace();
				}
//				boolean fg = GitReoisitory.hasSwitchedToCommit(gitString, commitId.get(i));
//				if (fg == false) {
//					Thread.sleep(1000);
//				}

				if (!projectJavaList.isEmpty()) {
					projectJavaList.clear();
				};
				File filename = new File(projectname);
				func(filename);
				for (int s = 0; s < projectJavaList.size(); s++) {
//	 		         System.out.println("222222222222");
					if (projectJavaList.get(s).endsWith(classPath.get(i))) {
						flag = true;
						javaPath = projectJavaList.get(s) + ".java";
//	                   break;
						File fileAfter = new File((projectJavaList.get(s) + ".java"));// 读取文件
						if (fileAfter.exists()) {
							CompilationUnit cu = getCompilationUnit(projectJavaList.get(s) + ".java");
							if (!fieldDeclarations.isEmpty()) {
								fieldDeclarations.clear();
							}
							getFieldDeclaration(cu, fieldDeclarations);
							for (int j = 0; j < fieldDeclarations.size(); j++) {
								FieldDeclaration fds = fieldDeclarations.get(j);
								VariableDeclarationFragment vdfs = (VariableDeclarationFragment) fds.fragments().get(0);
								String fieldNa = vdfs.getName().getIdentifier();

								if (fieldNa.equals(afterField.get(i))) {
									StringBuilder content = new StringBuilder();
									BufferedReader reader = new BufferedReader(new FileReader(javaPath));
									String line;
									while ((line = reader.readLine()) != null) {
							                content.append(line).append("\n");
							            }
							        reader.close();
									
									String dataPath =  "D:\\BIT_Report\\dataSource2\\"+nums+"_"+fieldNa+"_"+commitId.get(i)+".java";
									BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataPath,true)));
									out.write(content.toString());									
									out.close();
									flag=true;
									break;
								}
							}
						}
					}
				}
			}
			if(flag==false) {
				int lastPosition = classPath.get(i).lastIndexOf("\\");
				if(lastPosition>0){
				String classPathString = classPath.get(i).substring(0,lastPosition);
//				System.out.println("classPath"+classPath.get(i));
//				System.out.println("cover:"+classPathString);
				for (int s = 0; s < projectJavaList.size(); s++) {
					if (projectJavaList.get(s).endsWith(classPathString)) {
						System.out.println("文件存在："+classPath.get(i));
						File fileAfter = new File(classPathString+".java");
						if (fileAfter.exists()) {
							CompilationUnit cu = getCompilationUnit(projectJavaList.get(s) + ".java");
							if (!fieldDeclarations.isEmpty()) {
								fieldDeclarations.clear();
							}
							getFieldDeclaration(cu,fieldDeclarations);
							for(int j=0;j<fieldDeclarations.size();j++) {
								FieldDeclaration fds = fieldDeclarations.get(j);
								VariableDeclarationFragment vdfs = (VariableDeclarationFragment) fds.fragments().get(0);
								String fieldNa = vdfs.getName().getIdentifier();
								
								if(fieldNa.equals(afterField.get(i))) {
									StringBuilder content = new StringBuilder();
									BufferedReader reader = new BufferedReader(new FileReader(javaPath));
									String line;
									while ((line = reader.readLine()) != null) {
							                content.append(line).append("\n");
							            }
							        reader.close();
									 
									String dataSa =  "D:\\BIT_Report\\dataSource2\\"+nums+"_"+fieldNa+"_"+commitId.get(i)+".java";
									BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataSa,true)));
									out.write(content.toString());									
									out.close();
									}
								}
						}
						SaveData datasData0 = new SaveData();
						datasData0.setProjectNameString(projectName.get(i));
						datasData0.setCommitId(commitId.get(i));
						datasData0.setOldRe(beforeField.get(i));
						datasData0.setNewRe(afterField.get(i));
						datasData0.setType(saveafterType.get(i));
						datasData0.setClassPath(classPath.get(i));
						allDataSave.add(datasData0);
						}
					}
				}
			}
	        flag=false;
		}

		try {
			Array2CSV(allDataSave, saveCSV);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void Array2CSV(List<SaveData> data, String path) throws IOException {
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}

		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));

			for (SaveData dt : data) {
				String line = dt.getProjectNameString() + "," + dt.getCommitId() + "," + dt.getOldRe() + ","
						+ dt.getNewRe() + "," + dt.getType() + "," + dt.getClassPath();
				out.write(line + "\t\n");
			}

			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readFile(String scr) {

		File csv = new File(scr); // CSV文件路径
		if (!csv.exists()) {
			System.out.println("csv not find");
		} else {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(csv));
				String line = null;
				int index = 0;
				try {
					br.readLine();
					while ((line = br.readLine()) != null) {
						String item[] = line.split(",");
						projectName.add(item[0]);
						commitId.add(item[1]);
						beforeField.add(item[2]);// 获取：前边的Field名？？
						afterField.add(item[3]);
						saveafterType.add(item[4]);
						classPath.add(item[5].replace(".", "\\"));
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	// 获取所有文件路径
	private static void func(File file) {
		File[] fs = file.listFiles();
		if (fs == null || fs.length == 0) {
			return;
		}
		for (File f : fs) {
			if (f.isDirectory()) {
				func(f);
			}
			if (f.isFile()) {
				if (f.getName().endsWith(".java")) {// 判断名字
					projectJavaList.add(f.toString().replace(".java", ""));
				}
			}
		}
	}

	// 返回解析.java文件
	public static CompilationUnit getCompilationUnit(String javaFilePath) {
		byte[] input = null;
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(javaFilePath));
			input = new byte[bufferedInputStream.available()];
			bufferedInputStream.read(input);
			bufferedInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ASTParser astParser = ASTParser.newParser(AST.JLS17);
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		astParser.setSource(new String(input).toCharArray());
		astParser.setResolveBindings(true);
		astParser.setBindingsRecovery(true);
		CompilationUnit unit = (CompilationUnit) (astParser.createAST(null));
		return unit;
	}

	private static void getFieldAccess(ASTNode cuu, final List<FieldAccess> types) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(FieldAccess node) {
				types.add(node);
				return true;
			}
		});
	}

	public static void getFieldDeclaration(ASTNode cuu, final List<FieldDeclaration> types) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(FieldDeclaration node) {
				types.add(node);
				return true;
			}
		});
	}

	private static void getMethod(ASTNode cuu, final List<MethodDeclaration> types) {
		cuu.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(MethodDeclaration node) {
				types.add(node);
				return true;
			}
		});
	}
}

function OUT = polyrg(X,Y,m,Xtest,Ytest)

%X - Data from design variables for all observations
%Y - response to the data variables for the function of interest
%m - maximum order of desired polynomial regression coefficient


% Rotate matrix if 2nd dimension is larger than 1st
if size(X,2)>size(X,1)
   X=X';
end

Ns = size(X,1);           %Number of samples
Nv = size(X,2);           %Number of variables

%Initialize Matrix
A=zeros(m^Nv,Nv);

%All coefficients combinations
for i=1:Nv
    A(:,i)=mod(floor((1:m^Nv)/m^(i-1)),m);
end

%Flip matrix left to right
A=fliplr(A); 

%Delete terms bigger than m order / keep terms smaller or equal than m
A=A(sum(A,2)<=m,:); 

%Single variable terms of m order
Ab=diag(repmat(m,[1,Nv])); 

%Add maximum order terms to existant matrix of coefficient
A=[A;Ab];

%Initialize empty vector for polynomial term and coefficients
termPoly = cell(size(A,1),1);   %Number of coefficients rows

Nco = size(A,1);                 %Number of coefficients

for i=1:Nco
        curTerm=find(A(i,:));
        curStr='';
        for j=1:length(curTerm)
            if j==1
                curStr=[curStr,'x',num2str(curTerm(j))];       %Starts string 
            else
                curStr=[curStr,'.*x',num2str(curTerm(j))];     %Add times when there is more than 1 term   
            end
                
            if A(i,curTerm(j)) > 1                             %Add power when term is >1           
                curStr=[curStr,'.^',num2str(A(i,curTerm(j)))];
            end
        end
        termPoly{i,1}=curStr;
    end


%Organize eq to multiply values xm of each data point
MultipC = '1';
for i=1:Nv                                                         
     MultipC = strcat(MultipC,['.*C(:,',num2str(i),')']);       
end

%Initialize zero matrix 
Weig = zeros(Ns,Nco);

for i=1:Ns                                %For each data point
        cur = repmat(X(i,:),Nco,1);       %Repeat variable numbers for each coefficient
        C = cur.^A;                    %Power to coefficient exponents to get respective terms alone                 
        Weig(i,:) = eval(MultipC);
end

%Find estimated parameters Beta matrix
beta = Weig \ Y;

%Find estimate of approximate polynomial of sample points
Tr_Yappr= Weig*beta;

%Error between approximate and input data
Tr_r = Y-Tr_Yappr;  

%Test data for Cross Validation error - When there is input of test
test_data = exist('Xtest');

if test_data == 1
cur_Te = repmat(Xtest(1,:),Nco,1);
C = cur_Te.^A;  
Weig_Te(1,:) = eval(MultipC);

Yappr_Test= Weig_Te*beta;
delta_f_i = Ytest - Yappr_Test;         %f_i - f_hat_i(-i)
end

%Polynomial expression

for i=1:length(termPoly)
    if isempty(termPoly{i,1}) 
    termPoly{i,1} = [num2str(beta(i)) '.' num2str(1)];
    else 
    termPoly{i,1} = [num2str(beta(i)) '.' termPoly{i,1}];
    end
end

PolyExpre=char(termPoly);

% Arrange output files into a struc
if test_data == 1
    OUT = struct('NoSamples',Ns,'NoVariables',Nv,'CoeffsPower',A,'WeightsSample',Weig, ...
    'Coefficients', beta, 'TrainX',X, 'TrainY',Y, 'TestX', Xtest, 'TestY', Ytest,...
    'MaxOrder',m,'TrainYap',Tr_Yappr,'TrainErrorY',Tr_r,...
    'TestWeights',Weig_Te,'TestYap',Yappr_Test,'TestErrorYap',delta_f_i,'Expression',PolyExpre,...
    'MultipC',MultipC);
else
    OUT = struct('NoSamples',Ns,'NoVariables',Nv,'CoeffsPower',A,'WeightsSample',Weig, ...
    'Coefficients', beta, 'TrainX',X, 'TrainY',Y,'MaxOrder',m,'TrainYap',...
    Tr_Yappr,'TrainErrorY',Tr_r,'Expression',PolyExpre,'MultipC',MultipC);
end


end

